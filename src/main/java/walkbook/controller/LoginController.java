package walkbook.controller;

//@RestController
//@RequiredArgsConstructor
//public class LoginController {
//
//    public static final String LOGIN_USER = "LOGIN_MEMBER";
//    private final LoginService loginService;
//
//    //로그인
//    @PostMapping("/signin")
//    public LoginUserResponse signin(@Valid LoginUserRequest request, HttpServletRequest servletRequest) {
//        User loginUser = loginService.login(request.toUserEntity());
//
//        // 로그인 기능 -> session으로 관리
//        HttpSession session = servletRequest.getSession();
//        session.setAttribute(LOGIN_USER, loginUser);
//
//        System.out.println("session = " + session);
//
//        return new LoginUserResponse(loginUser.getUsername(), loginUser.getPassword());
//    }
//
//    //로그아웃
//    @GetMapping("/signout")
//    public void signout(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (session != null) {
////            session.removeAttribute(LOGIN_USER);
//            session.invalidate();
//        }
//    }
//}
