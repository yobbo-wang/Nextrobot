package wang.yobbo.sys.security;

import java.io.Serializable;

public class ShiroUser implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userID;
    private String userAccount;
    private String name;
    private String email;
    private String telePhone;
    private Boolean isAdmin;
    private String defaultRoleId;
    private String defaultRoleDesc;
    private String currentRoleId;
    private String currentRoleName;
    private String currentDeptId;
    private String currentDeptName;
    private String currentCorpId;
    private String currentCorpName;
    private String defaultTemplateId;
    private String defaultTempleteName;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelePhone() {
        return telePhone;
    }

    public void setTelePhone(String telePhone) {
        this.telePhone = telePhone;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public String getDefaultRoleId() {
        return defaultRoleId;
    }

    public void setDefaultRoleId(String defaultRoleId) {
        this.defaultRoleId = defaultRoleId;
    }

    public String getDefaultRoleDesc() {
        return defaultRoleDesc;
    }

    public void setDefaultRoleDesc(String defaultRoleDesc) {
        this.defaultRoleDesc = defaultRoleDesc;
    }

    public String getCurrentRoleId() {
        return currentRoleId;
    }

    public void setCurrentRoleId(String currentRoleId) {
        this.currentRoleId = currentRoleId;
    }

    public String getCurrentRoleName() {
        return currentRoleName;
    }

    public void setCurrentRoleName(String currentRoleName) {
        this.currentRoleName = currentRoleName;
    }

    public String getCurrentDeptId() {
        return currentDeptId;
    }

    public void setCurrentDeptId(String currentDeptId) {
        this.currentDeptId = currentDeptId;
    }

    public String getCurrentDeptName() {
        return currentDeptName;
    }

    public void setCurrentDeptName(String currentDeptName) {
        this.currentDeptName = currentDeptName;
    }

    public String getCurrentCorpId() {
        return currentCorpId;
    }

    public void setCurrentCorpId(String currentCorpId) {
        this.currentCorpId = currentCorpId;
    }

    public String getCurrentCorpName() {
        return currentCorpName;
    }

    public void setCurrentCorpName(String currentCorpName) {
        this.currentCorpName = currentCorpName;
    }

    public String getDefaultTemplateId() {
        return defaultTemplateId;
    }

    public void setDefaultTemplateId(String defaultTemplateId) {
        this.defaultTemplateId = defaultTemplateId;
    }

    public String getDefaultTempleteName() {
        return defaultTempleteName;
    }

    public void setDefaultTempleteName(String defaultTempleteName) {
        this.defaultTempleteName = defaultTempleteName;
    }
}
