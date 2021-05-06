package com.example.demo.user;

import javax.persistence.*;

/**
 * User Form for signing in/up
 */
@MappedSuperclass
public class UserForm {
    /**
     * The UserForm id
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    /**
     * The UserForm username input
     */
    @Column(nullable = false)
    private String username;

    /**
     * The UserForm plain-text password input
     */
    @Column(nullable = false)
    private String password;

    /**
     * The UserForm display name input
     */
    @Column(name = "display_name", columnDefinition = "varchar(25)")
    private String displayName;

    /**
     * The UserForm status attribute
     */
    @Column(name = "status", columnDefinition = "varchar(10) default 'online'")
    private String status = "online";

    /**
     * Constructor without input argument
     */
    public UserForm() {}

    /**
     * Constructor without input argument
     * @param username plain-text username input
     * @param password plain-text password input
     */
    public UserForm(String username, String password) {
        this.password = password;
        this.username = username;
    }

    /**
     * @return the plain-text username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the plain-text password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return the current status of the User Form object
     */
    public String getStatus() {
        return status;
    }

    /**
     * @return the display name of the user
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param password set the password to the given one
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @param username set the username to the given one
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @param status set the status to the given one
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @param displayName set displayName to the given one
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * @return the user id
     */
    public Long getId() {
        return id;
    }
}
