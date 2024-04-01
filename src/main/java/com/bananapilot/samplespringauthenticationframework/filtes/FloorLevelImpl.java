package com.bananapilot.samplespringauthenticationframework.filtes;

import java.util.List;

public abstract class FloorLevelImpl {
    public boolean isRoleGreaterOrEquals (String requiredRole, List<String> userRoles) {
        for(String userRole : userRoles) {
            if(isRoleInternalGreaterOrEquals(requiredRole, userRole)) {
                return true;
            }
        }
        return false;
    }

    protected abstract boolean isRoleInternalGreaterOrEquals(String requiredRole, String userRoles);
}
