package com.example.Liverpool_TicketSystem.config;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.example.Liverpool_TicketSystem.domain.User;
import com.example.Liverpool_TicketSystem.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

//CustomSuccessHandler là class dùng để xử lý logic sau khi người dùng đăng nhập thành công (login success). Nó cho phép bạn quyết định chuyển hướng (redirect) người dùng đến trang nào, dựa trên vai trò (ROLE) của họ.
//Lớp này implement AuthenticationSuccessHandler => bắt buộc phải override method onAuthenticationSuccess.
//AuthenticationSuccessHandler là một interface (giao diện) dùng để xử lý logic sau khi người dùng đăng nhập thành công (login success).

public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    // Vì sao userService vẫn có trong constructor?
    // Lý do duy nhất là:

    // Chuẩn bị cho tương lai hoặc để mở rộng chức năng.
    // Ví dụ: nếu sau khi người dùng đăng nhập, bạn muốn:
    // Ghi lại thời điểm đăng nhập cuối cùng.
    // Cập nhật trạng thái "online".
    // Ghi log lịch sử truy cập.
    // Kiểm tra trạng thái tài khoản, v.v...
    // Lúc đó bạn cần tương tác với database, và UserService là nơi chứa logic để
    // làm việc này.

    // À ra là truyền userService dô để sài phương thức layUserTheoEmail(email) để
    // lấy thông tin người dùng sau khi đăng nhập thành công.(đăng nhập thì sẽ dùng
    // email) .
    // Từ đó truyền thông tin username,... lên view (để hiển thị tên người dùng)

    private UserService userService;

    public CustomSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    // ===========================================================================
    // Đây là hàm xác định người dùng sẽ được chuyển hướng đến trang nào sau khi
    // đăng nhập thành công.
    protected String determineTargetUrl(final Authentication authentication) {

        // Tạo một map đơn giản ánh xạ giữa quyền (ROLE) và URL tương ứng:
        // Nếu có ROLE_USER → chuyển đến /
        // Nếu có ROLE_ADMIN → chuyển đến /admin
        Map<String, String> roleTargetUrlMap = new HashMap<>();
        roleTargetUrlMap.put("ROLE_USER", "/");
        roleTargetUrlMap.put("ROLE_ADMIN", "/admin");

        // Lấy danh sách các vai trò (roles/quyền) mà người dùng hiện tại có sau khi
        // đăng nhập thành công.

        // 🔹 authentication
        // Là một đối tượng của Spring Security đại diện cho người dùng đã đăng nhập.
        // Nó chứa thông tin như: username, password (đã mã hoá), danh sách quyền, trạng
        // thái tài khoản...

        // 🔹 authentication.getAuthorities()
        // Trả về danh sách quyền (roles) của người dùng.
        // Trả về kiểu: Collection<? extends GrantedAuthority>, tức là tập hợp các đối
        // tượng kiểu GrantedAuthority.

        // 🔹 GrantedAuthority
        // Là một interface trong Spring Security.
        // Mỗi quyền (role) như "ROLE_USER" hay "ROLE_ADMIN" sẽ được đại diện bởi 1
        // GrantedAuthority.

        // 🔹 Collection<? extends GrantedAuthority>
        // Đây là một danh sách các quyền mà người dùng hiện tại có.

        // ✅ Ví dụ minh hoạ đơn giản
        // Giả sử trong database bạn có user:

        // Username | Role
        // john | ROLE_USER
        // admin | ROLE_ADMIN

        // Khi john đăng nhập, authentication.getAuthorities() sẽ trả về:
        // [SimpleGrantedAuthority("ROLE_USER")]
        // Khi admin đăng nhập:
        // [SimpleGrantedAuthority("ROLE_ADMIN")]
        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // Lặp qua danh sách quyền, nếu tìm thấy quyền có trong map, thì trả về URL
        // tương ứng.

        // 1. for (final GrantedAuthority grantedAuthority : authorities)
        // Duyệt từng phần tử trong authorities — danh sách quyền (roles) của người
        // dùng.
        // Ví dụ user có 2 role: ROLE_USER và ROLE_ADMIN, vòng lặp sẽ chạy 2 lần, lần
        // lượt với mỗi role.

        // 2. String authorityName = grantedAuthority.getAuthority();
        // Lấy tên quyền (role) của phần tử hiện tại.
        // Ví dụ: "ROLE_USER" hoặc "ROLE_ADMIN".

        // 3. if (roleTargetUrlMap.containsKey(authorityName))
        // Kiểm tra xem trong bản đồ roleTargetUrlMap có chứa key là tên role này không.
        // roleTargetUrlMap là Map<String, String> chứa cặp role → URL, ví dụ:
        // "ROLE_USER" → "/"
        // "ROLE_ADMIN" → "/admin"

        // 4. return roleTargetUrlMap.get(authorityName);
        // Nếu tìm thấy role trong map, trả về URL tương ứng.
        // Ví dụ nếu authorityName = "ROLE_ADMIN", trả về "/admin".

        for (final GrantedAuthority grantedAuthority : authorities) {

            String authorityName = grantedAuthority.getAuthority();

            if (roleTargetUrlMap.containsKey(authorityName)) {
                return roleTargetUrlMap.get(authorityName);
            }
        }

        throw new IllegalStateException();
    }

    // ============================================================================

    // Thực hiện chuyển hướng bằng cách gửi lệnh HTTP redirect cho trình duyệt để nó
    // tải trang tương ứng.
    // (Đây là object giúp thực hiện việc chuyển hướng (redirect) người dùng)

    // RedirectStrategy là giao diện (interface) trong Spring Security, dùng để xử
    // lý việc chuyển hướng (redirect) HTTP.

    // HTTP là viết tắt của HyperText Transfer Protocol — tạm dịch là Giao thức
    // truyền tải siêu văn bản.Nói đơn giản, HTTP là ngôn ngữ, quy tắc mà trình
    // duyệt web và máy chủ web sử dụng để giao tiếp với nhau.

    // Mô tả đơn giản về HTTP:
    // Khi bạn nhập một địa chỉ web (URL) trên trình duyệt, ví dụ:
    // https://example.com, trình duyệt sẽ gửi một yêu cầu HTTP (HTTP request) đến
    // máy chủ chứa website đó.

    // Máy chủ sẽ xử lý yêu cầu rồi gửi lại một phản hồi HTTP (HTTP response), chứa
    // dữ liệu trang web như HTML, CSS, hình ảnh,...

    // Trình duyệt nhận dữ liệu và hiển thị trang web cho bạn.

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    // Đây là method được gọi khi người dùng đăng nhập thành công.

    // onAuthenticationSuccess là phương thức được định nghĩa trong interface
    // AuthenticationSuccessHandler.
    // Phương thức này được Spring Security tự động gọi ngay khi người dùng đăng
    // nhập thành công.

    // Tham số:
    // HttpServletRequest request: đối tượng chứa thông tin về yêu cầu HTTP hiện tại
    // (ví dụ: thông tin trình duyệt gửi lên, dữ liệu form đăng nhập,...)

    // HttpServletResponse response: đối tượng dùng để trả về phản hồi HTTP (ví dụ:
    // chuyển hướng trang, gửi dữ liệu,...)

    // Authentication authentication: chứa thông tin về người dùng đã đăng nhập, bao
    // gồm tên, role, quyền hạn,...
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        // Gọi lại hàm phía trên để xác định URL cần chuyển tới dựa vào role.
        // Kết quả trả về là một chuỗi String chứa đường dẫn URL, được lưu trong biến
        // targetUrl.
        String targetUrl = determineTargetUrl(authentication);

        // Kiểm tra xem phản hồi HTTP đã được commit chưa.
        // Nếu phản hồi đã được gửi đi rồi (đã commit), thì không thể tiếp tục redirect,
        // nên method sẽ dừng (return) để tránh lỗi.

        if (response.isCommitted()) {

            return;
        }

        // Nếu chưa gửi phản hồi, thì dùng redirectStrategy để thực hiện chuyển hướng
        // HTTP (HTTP redirect) đến URL được xác định trong targetUrl.
        // Nói cách khác, server sẽ gửi lệnh cho trình duyệt: "Hãy tải trang mới tại URL
        // này."

        redirectStrategy.sendRedirect(request, response, targetUrl);

        // Cần có phương thức này để xoá thông tin lỗi đăng nhập cũ khỏi session. (từ đó
        // khi truyền thông tin lên view (username, ...) nó mới hiển thị)

        // nếu bạn không gọi dòng này, có thể xảy ra lỗi hiển thị thông báo sai hoặc
        // session bị ghi đè — gián tiếp làm cho dữ liệu bạn set (như username) không
        // được hiển thị đúng.

        // Phương thức này được định nghĩa bên dưới á

        clearAuthenticationAttributes(request, authentication);

    }

    // ===========================================================================

    // 1. Khi KHÔNG sử dụng phương thức này
    // Bạn đăng nhập sai, Spring Security sẽ lưu thông tin lỗi vào session với key
    // WebAttributes.AUTHENTICATION_EXCEPTION.
    // Sau đó, bạn đăng nhập đúng, nếu không gọi clearAuthenticationAttributes,
    // thông tin lỗi vẫn còn trong session.
    // Khi bạn chuyển trang hoặc reload, có thể giao diện vẫn hiển thị thông báo lỗi
    // cũ (ví dụ: "Sai mật khẩu") dù bạn đã đăng nhập thành công.

    // 2. Khi CÓ sử dụng phương thức này
    // Sau khi đăng nhập thành công, phương thức này sẽ xoá thông tin lỗi đăng nhập
    // cũ khỏi session.
    // Khi bạn chuyển trang hoặc reload, sẽ không còn thông báo lỗi cũ xuất hiện
    // nữa.
    // Trải nghiệm người dùng sẽ đúng: đăng nhập thành công thì không còn báo lỗi
    // cũ.

    // Sau khi đăng nhập thành công, bạn sẽ không thấy trang đăng nhập nữa.
    // Nhưng việc xoá lỗi đăng nhập cũ khỏi session là để đảm bảo chắc chắn:
    // Không có thông báo lỗi cũ nào bị hiển thị lại ở bất kỳ trang nào sau khi đăng
    // nhập thành công.

    // Đây là best practice trong Spring Security để tránh hiển thị nhầm thông báo
    // lỗi cho người dùng.

    // ===============================================================

    // 1. HttpServletRequest request
    // Đây là đối tượng đại diện cho yêu cầu HTTP từ client (trình duyệt) gửi lên
    // server.

    // Bạn dùng request để:

    // Lấy session:
    // HttpSession session = request.getSession(false);
    // Lấy các tham số truyền lên từ form, URL,...
    // Truy cập cookie, header, IP,...

    // 👉 Trong trường hợp hàm clearAuthenticationAttributes(), nó dùng request để
    // truy cập HttpSession và xóa thông tin lỗi đăng nhập cũ:
    // session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

    // 2. Authentication authentication
    // Đây là đối tượng chứa thông tin xác thực của người dùng, được Spring Security
    // tạo ra sau khi xác thực thành công.

    // Nó cho bạn biết:
    // Người dùng là ai (username/email)
    // Có vai trò (role)/quyền gì
    // Đăng nhập bằng cách nào (form login, token,...)

    // authentication.getName(); // trả về username
    // authentication.getAuthorities(); // danh sách quyền (roles)

    protected void clearAuthenticationAttributes(HttpServletRequest request, Authentication authentication) {

        // Dòng này cố gắng **lấy session hiện tại** từ `request`.
        // `false` nghĩa là: **nếu không có session thì không tạo mới**.
        // → Tránh tạo session không cần thiết.

        HttpSession session = request.getSession(false);

        // Nếu không có session, thì thoát ra ngay (không làm gì cả).
        // Tránh gọi removeAttribute trên session null (tránh lỗi NullPointerException).

        if (session == null) {
            return;
        }

        // - Dòng này **xóa thuộc tính lỗi xác thực** khỏi session.
        // - `WebAttributes.AUTHENTICATION_EXCEPTION` là **hằng số** đại diện cho key
        // dùng để lưu lỗi xác thực trong Spring Security (thường là exception như
        // `BadCredentialsException`).
        // - Nếu không xóa đi, **lỗi cũ có thể bị hiển thị sai** ở lần login tiếp theo.

        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        // | Thành phần | Ý nghĩa |
        // |------------|--------|
        // | `HttpServletRequest request` | Yêu cầu HTTP từ client |
        // | `Authentication authentication` | Đối tượng chứa thông tin xác thực của
        // người dùng |
        // | `getSession(false)` | Lấy session hiện có, không tạo mới |
        // | `removeAttribute(...)` | Xóa lỗi xác thực khỏi session |

        // ===========================================================================

        // 🔹 1. HTTP dùng để làm gì?
        // HTTP là cầu nối giữa client và server. Ví dụ:

        // Khi bạn gõ một địa chỉ web như https://www.google.com, trình duyệt sẽ:

        // Gửi một yêu cầu (request) theo giao thức HTTP đến server của Google.

        // Server sẽ phản hồi (response) lại bằng nội dung trang web

        // 🔹 2. Cách hoạt động đơn giản của HTTP
        // Quá trình gồm 2 bước chính:

        // Client gửi HTTP request
        // → Ví dụ: “Tôi muốn lấy trang chủ của bạn!”

        // Server gửi HTTP response
        // → Ví dụ: “Đây là nội dung trang chủ!”

        // =============================================
        // authentication là một đối tượng của Spring Security, đại diện cho người dùng
        // đã đăng nhập thành công.

        // Cụ thể:
        // Khi người dùng đăng nhập, Spring Security sẽ xác thực thông tin và tạo ra một
        // đối tượng Authentication chứa:
        // Tên đăng nhập (username/email): lấy bằng authentication.getName()
        // Danh sách quyền (roles): lấy bằng authentication.getAuthorities()
        // Các thông tin xác thực khác (trạng thái tài khoản, chi tiết user...)

        // Đây là lí do truyền vô authentication
        // authentication.getName() lấy ra email (hoặc username) của người dùng vừa đăng
        // nhập thành công.
        String email = authentication.getName();

        // userService.layUserTheoEmail(email) truy vấn database để lấy đối tượng User
        // tương ứng với email đó.

        // Phải tiêm userService vào CustomSuccessHandler để có thể gọi phương thức
        // layUserTheoEmail(email).
        User user = this.userService.layUserTheoEmail(email);

        // session.setAttribute("username", user.getUsername()); sẽ lưu tên đăng nhập
        // (username) vào session.

        // session.getAttribute("username")
        // dùng để lấy lại giá trị đã lưu trong session với key "username".

        // Nhờ vậy, ở các trang JSP/view, bạn có thể lấy ra username bằng
        // ${sessionScope.username} để hiển thị tên người dùng.

        if (user != null) {
            session.setAttribute("username", user.getUsername());
        }

    }

    // ===========================================================================

}
