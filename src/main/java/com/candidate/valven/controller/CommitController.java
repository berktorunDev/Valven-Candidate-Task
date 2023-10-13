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
        // Endpoint for rendering the developer page

        // Add user-related information (GitHub and GitLab usernames and emails) to the
        // model
        model.addAttribute("githubUsername", githubUsername);
        model.addAttribute("githubEmail", githubEmail);
        model.addAttribute("gitlabUsername", gitlabUsername);
        model.addAttribute("gitlabEmail", gitlabEmail);

        return "developer.html"; // Thymeleaf template name: developer.html
    }

    @GetMapping("/commitList")
    public String showCommitList(Model model) {
        // Endpoint for rendering the commit list page

        // Retrieve the commit list and add it to the model
        List<CommitDTO> commitList = commitService.getCommitsFromGitHubAndGitLab();
        model.addAttribute("commitList", commitList);

        return "commitList"; // Thymeleaf template name: commitList.html
    }

    @GetMapping("/commitDetail/{id}")
    public String showCommitDetail(@PathVariable Long id, Model model) {
        // Endpoint for rendering the commit detail page for a specific commit

        // Retrieve the details of a specific commit by its ID and add them to the model
        CommitDTO commitDTO = commitService.getCommitById(id);
        model.addAttribute("commitDTO", commitDTO);

        return "commitDetail"; // Thymeleaf template name: commitDetail.html
    }
}
