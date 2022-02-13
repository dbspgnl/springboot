package com.sample.spring.controller;

// import java.io.IOException;

// import com.sample.spring.service.FacePainterExample;

// import com.sample.spring.service.WebcamViewerExample;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebcamController {

    // @Autowired
    // WebcamViewerExample webcamViewerExample;


    @GetMapping("/cam")
    public void index() {
        // WebcamViewerExample webcam = new WebcamViewerExample();
        // webcam.run();
        // try {
        //     new FacePainterExample();
        // } catch (IOException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }

        // return "contents/main/index";
    }
    
}
