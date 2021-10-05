package co.luism.backend.enterprise;


import co.luism.common.IotUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.persistence.*;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class User {
    @Transient
    private static final Logger log = LoggerFactory.getLogger(User.class);
    @Transient
    private static final int ITERATION_NUMBER = 1000;
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    @Column(
            unique = true,
            nullable = false
    )
    private Integer userId = 0;
    @Column(
            unique = true,
            nullable = false,
            length = 32
    )
    private String login = "";
    @Column(
            nullable = true,
            length = 32
    )
    private String firstName = "";
    @Column(
            nullable = true,
            length = 32
    )
    private String lastName = "";

    @ManyToOne(
            targetEntity = Organization.class
    )
    @JoinColumn(
            name = "organizationId",
            insertable = false,
            updatable = false,
            nullable = false
    )
    private Organization myOrganization;
    @Column
    private Integer organizationId = 1;
    @OneToMany(
            cascade = {CascadeType.ALL},
            fetch = FetchType.EAGER
    )
    @Fetch(FetchMode.SELECT)
    @JoinColumn(
            name = "userId"
    )
    @MapKey(
            name = "vehicleId"
    )
    private Map<String, VehicleFavourite> favouriteHashMap = new HashMap();

    @ManyToOne(
            targetEntity = Role.class
    )
    @JoinColumn(
            name = "roleId",
            insertable = false,
            updatable = false,
            nullable = false
    )
    private Role myRole;
    @Column
    private Integer roleId = 1;
    @Column(
            nullable = false,
            length = 64
    )
    private String email = "";
    @Column(
            nullable = false,
            length = 32
    )
    private String password = "none";
    @Column(
            nullable = false,
            length = 32
    )
    private String salt;
    @Column(
            nullable = false,
            length = 16
    )
    private String language = "en";
    @Column
    private String updateBy = "";

    public User() {
    }

    //private String scramble(String in) {
      //  return IotUtils.md5(DiagnosticsConfig.getInstance().getSalt() + in);
    //}

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Organization getMyOrganization() {
        return this.myOrganization;
    }

    public void setMyOrganization(Organization organizationId) {
        this.myOrganization = organizationId;
    }

    public Integer getOrganizationId() {
        return this.organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
        this.generatePassword();
    }

    public String getSalt() {
        return this.salt;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Role getMyRole() {
        return this.myRole;
    }

    public void setMyRole(Role myRole) {
        this.myRole = myRole;
    }

    public Integer getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getUpdateBy() {
        return this.updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Map<String, VehicleFavourite> getFavouriteHashMap() {
        return this.favouriteHashMap;
    }

    public void setFavouriteHashMap(Map<String, VehicleFavourite> favouriteHashMap) {
        this.favouriteHashMap = favouriteHashMap;
    }

    public boolean create() {
        this.generatePassword();
        //return super.create();
        return true;
    }

    private void generatePassword() {
        SecureRandom random = null;

        try {
            random = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException var7) {
            log.error(String.valueOf(var7));
        }

        byte[] bSalt = new byte[8];
        random.nextBytes(bSalt);
        byte[] bDigest = new byte[0];

        try {
            bDigest = IotUtils.getHash(1000, this.password, bSalt);
        } catch (NoSuchAlgorithmException var5) {
            log.error(String.valueOf(var5));
        } catch (UnsupportedEncodingException var6) {
            log.error(String.valueOf(var6));
        }

        this.password = IotUtils.byteToBase64(bDigest);
        this.salt = IotUtils.byteToBase64(bSalt);
    }

    public boolean authenticate(String thePassword) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (this.password == null) {
            this.password = "000000000000000000000000000=";
        }

        if (this.salt == null) {
            this.salt = "00000000000=";
        }

        byte[] bDigest = IotUtils.base64ToByte(this.password);
        byte[] bSalt = IotUtils.base64ToByte(this.salt);
        byte[] proposedDigest = IotUtils.getHash(1000, thePassword, bSalt);
        return Arrays.equals(proposedDigest, bDigest);
    }

    public boolean equals(Object other) {
        return other instanceof User && this.getLogin().equals(((User)other).getLogin());
    }

    public int hashCode() {
        int hash_n = 3;
        int hash = 7 * hash_n + this.login.hashCode();
        return hash;
    }

    public boolean isFavourite(String vehicleId) {
        VehicleFavourite vf = (VehicleFavourite)this.favouriteHashMap.get(vehicleId);
        return vf == null ? false : vf.getFavourite();
    }

    public void setFavourite(String vehicleId, Boolean favourite) {

    }
}

