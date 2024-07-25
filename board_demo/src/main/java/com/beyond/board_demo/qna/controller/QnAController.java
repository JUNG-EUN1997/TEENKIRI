//package com.beyond.board_demo.qna.controller;
//
//import com.beyond.board_demo.comment.service.CommentService;
//import com.beyond.board_demo.qna.dto.QnAAnswerReqDto;
//import com.beyond.board_demo.qna.dto.QnADetailDto;
//import com.beyond.board_demo.qna.dto.QnAListResDto;
//import com.beyond.board_demo.qna.dto.QnASaveReqDto;
//import com.beyond.board_demo.qna.service.QnAService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("qna")
//public class QnAController {
//
//    private final QnAService qnAService;
//    private final CommentService commentService;
//
//    @Autowired
//    public QnAController(QnAService qnAService, CommentService commentService) {
//        this.qnAService = qnAService;
//        this.commentService = commentService;
//    }
//
//    @GetMapping("create")
//    public String createQuestionForm() {
//        return "qna/create";
//    }
//
//    @PostMapping("create")
//    public String createQuestion(@ModelAttribute QnASaveReqDto dto, Model model) {
//        try {
//            qnAService.createQuestion(dto);
//            return "redirect:/qna/list";
//        } catch (IllegalArgumentException e) {
//            model.addAttribute("errorMessage", e.getMessage());
//            return "qna/create";
//        }
//    }
//
//    @GetMapping("list")
//    public String getAllQuestions(Model model) {
//        List<QnAListResDto> questions = qnAService.qnaList();
//        model.addAttribute("questions", questions);
//        return "qna/list";
//    }
//
//    //    @GetMapping("detail/{id}")
////    public String getQuestionDetail(@PathVariable Long id, Model model) {
////        QnADetailDto questionDetail = QnADetailDto.fromEntity(qnAService.getQuestionDetail(id));
////        model.addAttribute("question", questionDetail);
////        return "qna/detail";
////    }
////    @GetMapping("detail/{id}")
////    public String getQuestionDetail(@PathVariable Long id, Model model) {
////        QnADetailDto qnaDetail = QnADetailDto.fromEntity(qnAService.getQuestionDetail(id));
////        model.addAttribute("qna", qnaDetail);
////        List<CommentResDto> comments = commentService.getCommentsByQnaId(id);
////        model.addAttribute("comments", comments);
////        return "qna/detail";
////    }
//    @GetMapping("/detail/{id}")
//    public String getQuestionDetail(@PathVariable Long id, Model model) {
//        QnADetailDto question = QnADetailDto.fromEntity(qnAService.getQuestionDetail(id));
////        List<CommentSaveReqDto> comments = qnAService.getCommentsByQnAId(id);
//        model.addAttribute("question", question);
//        return "qna/detail";
//    }
//
//    @GetMapping("answer/{id}")
//    public String answerQuestionForm(@PathVariable Long id, Model model) {
//        QnADetailDto questionDetail = QnADetailDto.fromEntity(qnAService.getQuestionDetail(id));
//        model.addAttribute("question", questionDetail);
//        return "qna/answer";
//    }
//
//    @PostMapping("answer/{id}")
//    public String answerQuestion(@PathVariable Long id, @ModelAttribute QnAAnswerReqDto dto) {
//        qnAService.answerQuestion(id, dto);
//        return "redirect:/qna/detail/" + id;
//    }
//}
package com.beyond.board_demo.qna.controller;

import com.beyond.board_demo.comment.dto.CommentSaveReqDto;
import com.beyond.board_demo.comment.service.CommentService;
import com.beyond.board_demo.post.dto.PostUpdateDto;
import com.beyond.board_demo.qna.dto.*;
import com.beyond.board_demo.qna.service.QnAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("qna")
public class QnAController {

    private final QnAService qnAService;
    private final CommentService commentService;

    @Autowired
    public QnAController(QnAService qnAService, CommentService commentService) {
        this.qnAService = qnAService;
        this.commentService = commentService;
    }

    @GetMapping("create")
    public String createQuestionForm() {
        return "qna/create";
    }

    @PostMapping("create")
    public String createQuestion(@ModelAttribute QnASaveReqDto dto, Model model) {
        try {
            qnAService.createQuestion(dto);
            return "redirect:/qna/list";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "qna/create";
        }
    }

    @GetMapping("list")
    public String getAllQuestions(Model model) {
        model.addAttribute("questions", qnAService.qnaList());
        return "qna/list";
    }

    @GetMapping("detail/{id}")
    public String getQuestionDetail(@PathVariable Long id, Model model) {
        QnADetailDto question = QnADetailDto.fromEntity(qnAService.getQuestionDetail(id));
        model.addAttribute("question", question);
        model.addAttribute("comments", commentService.getCommentsByQnaId(id));
        return "qna/detail";
    }

    @PostMapping("comment/create")
    public String createQnaComment(@ModelAttribute CommentSaveReqDto dto, RedirectAttributes redirectAttributes) {
        commentService.saveComment(dto);
        redirectAttributes.addAttribute("id", dto.getQnaId());
        return "redirect:/qna/detail/{id}";
    }

    @GetMapping("answer/{id}")
    public String answerQuestionForm(@PathVariable Long id, Model model) {
        QnADetailDto questionDetail = QnADetailDto.fromEntity(qnAService.getQuestionDetail(id));
        model.addAttribute("question", questionDetail);
        return "qna/answer";
    }

    @PostMapping("answer/{id}")
    public String answerQuestion(@PathVariable Long id, @ModelAttribute QnAAnswerReqDto dto) {
        qnAService.answerQuestion(id, dto);
        return "redirect:/qna/detail/" + id;
    }


    @PostMapping("update/question/{id}")
    public String qnaQUpdate(@PathVariable Long id, @ModelAttribute QnAQtoUpdateDto dto){
        qnAService.QnAQUpdate(id, dto);
        return "redirect:/qna/detail/" + id;
    }

    @PostMapping("update/answer/{id}")
    public String qnaAUpdate(@PathVariable Long id, @ModelAttribute QnAAtoUpdateDto dto){
        qnAService.QnAAUpdate(id, dto);
        return "redirect:/qna/detail/" + id;
    }

    @GetMapping("delete/{id}")
    public String qnaDelete(@PathVariable Long id){
        qnAService.qnaDelete(id);
        return "redirect:/qna/list";
    }
}


