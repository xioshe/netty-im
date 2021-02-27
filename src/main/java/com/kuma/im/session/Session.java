package com.kuma.im.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Session
 *
 * @author kuma 2021-02-27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Session {

    private String userId;
    private String username;
}
