package com.candidate.valven.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.candidate.valven.dto.CommitDTO;
import com.candidate.valven.service.CommitService;

@Controller
public class CommitController {
    private final CommitService commitService;

    @Value("${github.username}")
    private String githubUsername;

    @Value("${github.email}")
    private String githubEmail;

    @Value("${gitlab.username}")
    private String gitlabUsername;

    @Value("${gitlab.email}")
    private String gitlabEmail;

    public CommitController(CommitService commitService) {
        this.commitService = commitService;
    }

    @GetMapping("/developer")
    public String showDeveloperPage(Model model) {
        // Kullanıcı adı ve e-posta bilgilerini model'e ekleyin
        model.addAttribute("githubUsername", githubUsername);
        model.addAttribute("githubEmail", githubEmail);
        model.addAttribute("gitlabUsername", gitlabUsername);
        model.addAttribute("gitlabEmail", gitlabEmail);

        return "developer.html"; // developer.html Thymeleaf şablon adı
    }

    @GetMapping("/commitList")
    public String showCommitList(Model model) {
        // Commit listesini çekerek model'e ekleyin
        List<CommitDTO> commitList = commitService.getCommitsFromGitHubAndGitLab();
        model.addAttribute("commitList", commitList);

        return "commitList"; // commitList.html Thymeleaf şablon adı
    }

    @GetMapping("/commitDetail/{id}")
    public String showCommitDetail(@PathVariable Long id, Model model) {
        // Belirli bir commitin detaylarını çekerek model'e ekleyin
        CommitDTO commitDTO = commitService.getCommitById(id);
        model.addAttribute("commitDTO", commitDTO);

        return "commitDetail"; // commitDetail.html Thymeleaf şablon adı
    }
}
