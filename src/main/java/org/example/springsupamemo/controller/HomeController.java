package org.example.springsupamemo.controller;

import lombok.RequiredArgsConstructor;
import org.example.springsupamemo.dto.MemoFormDTO;
import org.example.springsupamemo.service.MemoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping // '/'
@RequiredArgsConstructor
public class HomeController {
    private final MemoService memoService;

//    @Autowired
//    public HomeController(MemoService memoService) {
//        this.memoService = memoService;
//    }

    @GetMapping
//    public String home() {
    public String home(Model model) {
        // service를 통해서 추가된 결과를 새로 호출
        model.addAttribute("memoList", memoService.findAll());
        return "home";
    }

    @PostMapping
    public String memoForm(
            @ModelAttribute MemoFormDTO memoFormDTO
//            , HttpSession session
    ) {
//        List<MemoViewDTO> memoList = (List<MemoViewDTO>) session.getAttribute("memoList");
//        if (memoList == null) {
//            memoList = new ArrayList<>();
//        }
//        memoList.add(new MemoViewDTO(memoFormDTO.memo()));
//        session.setAttribute("memoList", memoList);
        memoService.save(memoFormDTO); // 수파베이스로 가면...
        return "redirect:/"; // GET / -> home
    }
}
