package com.test.axiata.utils;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpSession;

@Service
public class SessionService {

    // Menyimpan username di session
    public void setUsernameInSession(HttpSession session, String username) {
        session.setAttribute("username", username);
    }

    // Mengambil username dari session
    public String getUsernameFromSession(HttpSession session) {
        if (session.getAttribute("username") == null) {
            // Jika tidak ada, set default value untuk username
            session.setAttribute("username", "ADMINISTRATOR");
        }
        return (String) session.getAttribute("username");
    }

    // Mengubah username di session
    public void updateUsernameInSession(HttpSession session, String newUsername) {
        session.setAttribute("username", newUsername);
    }

    // Menghapus username dari session
    public void removeUsernameFromSession(HttpSession session) {
        session.removeAttribute("username");
    }
}
