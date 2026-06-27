package simple_jwt.app.utilities.Enums;

import org.springframework.security.core.GrantedAuthority;

public enum AppUserRoles implements GrantedAuthority {
    USER,
    ADMIN;

    @Override
    public String getAuthority() { 
        return name();
    }
}
