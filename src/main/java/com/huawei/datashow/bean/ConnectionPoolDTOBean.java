package com.huawei.datashow.bean;

import lombok.Data;
import org.springframework.stereotype.Component;
import javax.validation.constraints.NotBlank;

@Component
@Data
public class ConnectionPoolDTOBean
{
    /**
     * pollName
     */
    @NotBlank
    private String pollName;

    /**
     * JDBC driver class
     */
    @NotBlank
    private String driverClassName;

    /**
     * JDBC url
     */
    @NotBlank
    private String url;

    /**
     * JDBC username
     */
    @NotBlank
    private String username;

    /**
     * JDBC password
     */
    @NotBlank
    private String password;

}
