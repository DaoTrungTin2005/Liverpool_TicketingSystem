package com.example.Liverpool_TicketSystem.domain.dto;

import com.example.Liverpool_TicketSystem.service.validator.RegisterChecked;

// ý tưởng là bắt email đã tồn tại ở cái create tài khoản (ở trang admin)
// mà cái RegisterChecked ban đầu chỉ để cho thăng RegisterDTO nó xài thôi (thnawgf này xài lúc signup (check email tồn tại và so sánh confirmpass vs pass))
//Bây giờ muốn bắt email đã tồn tại ở cái create tài khoản (ở trang admin) thì tạo thêm CreateAccountDTO và CreateAccountValidate (ở RegisterChecked sẽ đăng kí cái createAccountValidate)

// Có 1 vấn đề phát sinh là ban đầu ta tạo 2 bằng User và Role (ở Signup ta ko có cho phân quyển Role là ADmin hhay User)
// Cái này thì ta cho chọn Role nên hơi khó xử
//Ý tưởng là ta tạo cái roleName tưởng tự ở JSP : <form:radiobutton path="roleName" value="ADMIN" />, <form:radiobutton path="roleName" value="USER" />
// Bây giờ để trả về Role đẩy đủ bên COntroller chỉ cần :
// Role role = userService.layRoleTheoTen(createAccountDTO.getRoleName());
// user.setRole(role);

@RegisterChecked
public class CreateAccountDTO {

    private String username;
    private String password;
    private String email;
    private String roleName; // ví dụ: "ADMIN" hoặc "USER"

    public String getRoleName() {
        return roleName;
    }

    public String getUsername() {
        return username;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
