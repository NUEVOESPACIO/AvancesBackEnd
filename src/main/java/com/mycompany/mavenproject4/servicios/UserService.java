package com.mycompany.mavenproject4.servicios;
import com.mycompany.mavenproject4.dto.GeneralResponseOk;
import com.mycompany.mavenproject4.dto.UserCreateRequest;
import com.mycompany.mavenproject4.entidades.Role;
import com.mycompany.mavenproject4.entidades.RoleName;
import com.mycompany.mavenproject4.entidades.User;
import com.mycompany.mavenproject4.exception.DefaultImageUserNotFoundException;
import com.mycompany.mavenproject4.exception.EmailAlreadyExistsException;
import com.mycompany.mavenproject4.exception.InvalidRoleException;
import com.mycompany.mavenproject4.exception.ResolverFotoIOException;
import com.mycompany.mavenproject4.exception.RoleNotFoundException;

import com.mycompany.mavenproject4.repository.RoleRepository;
import com.mycompany.mavenproject4.repository.UserRepository;
import com.mycompany.mavenproject4.utils.comprimirJpegToBytes;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public GeneralResponseOk<Long> createUser(UserCreateRequest request) {

        // 1. Validar email único
        if (userRepository.existsByEmail(request.getEmail())) {

            throw new EmailAlreadyExistsException();

        }

        // 2. Validar username único
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyExistsExceptions();
        }

        // 3. Buscar rol
        RoleName roleName;
        try {
            roleName = RoleName.valueOf(request.getRoleName());
        } catch (IllegalArgumentException e) {
            throw new InvalidRoleException();
        }

        Role role = roleRepository.findByNombre(roleName)
                .orElseThrow(() -> new RoleNotFoundException());

        // 4. Crear usuario
        User user = new User();
        user.setUsername(request.getUsername());
        user.setNombre(request.getNombre());
        user.setApellido(request.getApellido());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPerfilAcademico(request.getPerfilAcademico());
        user.setRole(role);

        try {
            byte[] fotoFinal = resolverFoto(request.getFoto());
            user.setFoto(fotoFinal);
            user.setMimeType("image/jpeg");
        } catch (IOException e) {

            throw new ResolverFotoIOException();

        }

        // 5. Guardar
        userRepository.save(user);
        
        

     return new GeneralResponseOk<>("Usuario creado correctamente", user.getId());
    }

    public byte[] resolverFoto(byte[] fotoOriginal) throws IOException {

        if (fotoOriginal != null && fotoOriginal.length > 0) {
            // comprimir la imagen enviada
            return comprimirJpegToBytes.comprimirJpeg(fotoOriginal, 0.7f);
        }

        // si no hay foto → cargar imagen por defecto
        try ( InputStream is = getClass().getResourceAsStream("/static/default-user.jpg")) {
            if (is == null) {
                throw new DefaultImageUserNotFoundException();
            }
            byte[] defaultImage = is.readAllBytes();

            // opcional: también la comprimís por consistencia
            return comprimirJpegToBytes.comprimirJpeg(defaultImage, 0.7f);
        }
    }
}
