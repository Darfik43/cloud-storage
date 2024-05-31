package com.darfik.cloudstorage.domain.s3storage.util;

import org.springframework.stereotype.Service;

@Service
public class UserFolderResolver {

    public static String getUserFolderPrefix(Long id) {
        return "user-" + id + "-files/";
    }

}
