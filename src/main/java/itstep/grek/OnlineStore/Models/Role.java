package itstep.grek.OnlineStore.Models;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,
    CLIENT,
    ADMIN,
    MANAGER;

    @Override
    public  String getAuthority(){
        return name();
    }
}


