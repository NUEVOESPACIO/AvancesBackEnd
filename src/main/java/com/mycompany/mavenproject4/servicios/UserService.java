package com.mycompany.mavenproject4.servicios;

import com.mycompany.mavenproject4.dto.GeneralResponseOk;
import com.mycompany.mavenproject4.dto.UserCreateRequest;
import com.mycompany.mavenproject4.dto.UserInfo;
import com.mycompany.mavenproject4.dto.UserUpdateRequest;
import com.mycompany.mavenproject4.entidades.Role;
import com.mycompany.mavenproject4.entidades.RoleName;
import com.mycompany.mavenproject4.entidades.User;
import com.mycompany.mavenproject4.exception.DefaultImageUserNotFoundException;
import com.mycompany.mavenproject4.exception.EmailAlreadyExistsException;
import com.mycompany.mavenproject4.exception.InvalidRoleException;
import com.mycompany.mavenproject4.exception.ResolverFotoIOException;
import com.mycompany.mavenproject4.exception.RoleNotFoundException;
import com.mycompany.mavenproject4.exception.SoftDeleteNotAllowedException;
import com.mycompany.mavenproject4.exception.UserNotFoundException;
import com.mycompany.mavenproject4.exception.UsernameAlreadyExistsException;

import com.mycompany.mavenproject4.repository.RoleRepository;
import com.mycompany.mavenproject4.repository.UserRepository;
import com.mycompany.mavenproject4.utils.comprimirJpegToBytes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    // =========================
    // CREATE
    // =========================
    public GeneralResponseOk<Long> createUser(UserCreateRequest request) {

        validateEmail(request.getEmail());
        validateUsername(request.getUsername());

        Role role = getRoleFromRequest(request.getRoleName());

        User user = new User();
        mapBasicFields(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);
        user.setFoto(resolvePhotoSafe(request.getFoto()));
        user.setMimeType("image/jpeg");

        userRepository.save(user);

        return new GeneralResponseOk<>("Usuario creado correctamente", user.getId());
    }

    // =========================
    // UPDATE
    // =========================
    public GeneralResponseOk<Long> updateUser(Long id, UserUpdateRequest request) {

        User user = findUserOrThrow(id);

        updateEmailIfChanged(user, request.getEmail());
        updateUsernameIfChanged(user, request.getUsername());

        mapBasicFields(user, request);

        updatePasswordIfPresent(user, request.getPassword());
        updateRoleIfPresent(user, request.getRoleName());
        updatePhotoIfPresent(user, request.getFoto());

        userRepository.save(user);

        return new GeneralResponseOk<>("Usuario actualizado correctamente", user.getId());
    }

    // =========================
    // DELETE (SOFT DELETE)
    // =========================
    public GeneralResponseOk deleteUser(Long id) {

        User user = findUserOrThrow(id);

        validateDelete(user);

        userRepository.delete(user);

        return new GeneralResponseOk<>("Usuario eliminado correctamente", user.getId());
    }
    
    // =========================
    // LIST USERS
    // =========================
    public Page<UserInfo> listUsers(Pageable pageable) {

        return userRepository.findAll(pageable)
                .map(this::mapToUserInfo);
    }
    


    private UserInfo mapToUserInfo(User user) {
        UserInfo dto = new UserInfo();
        dto.setUsername(user.getUsername());
        dto.setNombre(user.getNombre());
        dto.setApellido(user.getApellido());
        dto.setEmail(user.getEmail());
        dto.setPerfilAcademico(user.getPerfilAcademico());
        dto.setFoto(user.getFoto());
        dto.setRoleName(user.getRole().getNombre().name());
        return dto;
    }

    // =========================
    // 🔹 PRIVATE HELPERS
    // =========================
    private User findUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    private void validateDelete(User user) {
        if (user.isAdmin()) {
            throw new SoftDeleteNotAllowedException();
        }
    }

    private void validateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException();
        }
    }

    private void validateUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new UsernameAlreadyExistsException();
        }
    }

    private Role getRoleFromRequest(String roleNameStr) {
        try {
            RoleName roleName = RoleName.valueOf(roleNameStr);
            return roleRepository.findByNombre(roleName)
                    .orElseThrow(RoleNotFoundException::new);
        } catch (IllegalArgumentException e) {
            throw new InvalidRoleException();
        }
    }

    private void mapBasicFields(User user, UserCreateRequest request) {
        user.setUsername(request.getUsername());
        user.setNombre(request.getNombre());
        user.setApellido(request.getApellido());
        user.setEmail(request.getEmail());
        user.setPerfilAcademico(request.getPerfilAcademico());
    }

    private void mapBasicFields(User user, UserUpdateRequest request) {
        user.setNombre(request.getNombre());
        user.setApellido(request.getApellido());
        user.setPerfilAcademico(request.getPerfilAcademico());
    }

    private void updateEmailIfChanged(User user, String newEmail) {
        if (!user.getEmail().equals(newEmail)) {
            validateEmail(newEmail);
            user.setEmail(newEmail);
        }
    }

    private void updateUsernameIfChanged(User user, String newUsername) {
        if (!user.getUsername().equals(newUsername)) {
            validateUsername(newUsername);
            user.setUsername(newUsername);
        }
    }

    private void updatePasswordIfPresent(User user, String password) {
        if (password != null && !password.isBlank()) {
            user.setPassword(passwordEncoder.encode(password));
        }
    }

    private void updateRoleIfPresent(User user, String roleNameStr) {
        if (roleNameStr != null) {
            user.setRole(getRoleFromRequest(roleNameStr));
        }
    }

    private void updatePhotoIfPresent(User user, byte[] foto) {
        if (foto != null && foto.length > 0) {
            user.setFoto(resolvePhotoSafe(foto));
            user.setMimeType("image/jpeg");
        }
    }

    private byte[] resolvePhotoSafe(byte[] foto) {
        try {
            return resolverFoto(foto);
        } catch (IOException e) {
            throw new ResolverFotoIOException();
        }
    }

    // =========================
    // FOTO
    // =========================
    public byte[] resolverFoto(byte[] fotoOriginal) throws IOException {

        if (fotoOriginal != null && fotoOriginal.length > 0) {
            return comprimirJpegToBytes.comprimirJpeg(fotoOriginal, 0.7f);
        }

        try ( InputStream is = getClass().getResourceAsStream("/static/default-user.jpg")) {
            if (is == null) {
                throw new DefaultImageUserNotFoundException();
            }
            byte[] defaultImage = is.readAllBytes();
            return comprimirJpegToBytes.comprimirJpeg(defaultImage, 0.7f);
        }
    }
}
