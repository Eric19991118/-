import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.bank.model.User;
import com.example.bank.repository.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(String username, String email, String password) {
        // 將密碼進行加密
        String encryptedPassword = passwordEncoder.encode(password);

        // 創建新的使用者物件
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(encryptedPassword);

        // 儲存使用者到資料庫
        userRepository.save(user);
    }

    public boolean authenticateUser(String username, String password) {
        // 根據使用者名稱從資料庫中獲取使用者物件
        User user = userRepository.findByUsername(username);

        // 檢查使用者是否存在並驗證密碼
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return true; // 登入驗證通過
        } else {
            return false; // 登入驗證失敗
        }
    }
}
