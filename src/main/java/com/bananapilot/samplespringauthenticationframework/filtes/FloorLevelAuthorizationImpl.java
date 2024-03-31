package com.bananapilot.samplespringauthenticationframework.filtes;

import java.util.List;

public class FloorLevelAuthorizationImpl {

    public boolean isFloorRoleEqualOrGreaterTo(String requiredRole, List<String> userRoles) {
        for(String userRole : userRoles) {
            if(isInternalFloorRoleEqualOrGreaterTo(requiredRole, userRole)) {
                return true;
            }
        }
        return false;
    }

    protected boolean isInternalFloorRoleEqualOrGreaterTo(String requiredRole, String userRoles) {
        return false;
    };

}
