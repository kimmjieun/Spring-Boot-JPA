package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
// 이거 붙이는 이유 이해하고 들어야한다
// 컨트롤러 클래스에 @Controller 어노테이션을 작성합니다.
// 해당 어노테이션이 적용된 클래스는 "Controller"임을 나타나고, bean으로 등록되며 해당 클래스가 Controller로 사용됨을 Spring Framework에 알립니다.
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model){
        // 모델 데이터를 뷰로 넘길 수 있도록
        model.addAttribute("data","hello!!!!");
        return "hello"; // 화면 이름
    }
}
