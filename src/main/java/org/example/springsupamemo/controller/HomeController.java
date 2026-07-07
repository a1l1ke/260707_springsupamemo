package org.example.springsupamemo.controller;

import jakarta.servlet.http.HttpSession;
import org.example.springsupamemo.dto.MemoFormDTO;
import org.example.springsupamemo.dto.MemoViewDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping // '/'
public class HomeController {
    @GetMapping
    public String home() {
        return "home";
    }

    @PostMapping
    public String memoForm(
            @ModelAttribute MemoFormDTO memoFormDTO,
            HttpSession session) {
        List<MemoViewDTO> memoList = (List<MemoViewDTO>) session.getAttribute("memoList");
        if (memoList == null) {
            memoList = new ArrayList<>();
        }
        memoList.add(new MemoViewDTO(memoFormDTO.memo()));
        session.setAttribute("memoList", memoList);
        return "redirect:/"; // GET / -> home
    }
}
