package com.synesis.mofl.lnm.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.synesis.mofl.lnm.security.UserPrincipal;
/**
 * @author Md. Emran Hossain
 * @since 25 Jan, 2022
 * @version 1.1
 */
public class UserHelper {

    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(UserHelper.class);

    /**
     * This method returns login user
     * 
     * @author Md. Emran Hossain
     * @return Long - userId
     * @throws Exception - Exception
     * @since 25 Jan, 2022
     * @version 1.1
     */
    public static Long getUserId() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            Long userId = userPrincipal.getId();
            return userId;
        }
        return null;
    }
}
