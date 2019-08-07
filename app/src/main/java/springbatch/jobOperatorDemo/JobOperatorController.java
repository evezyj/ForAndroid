package springbatch.jobOperatorDemo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("job2")
public class JobOperatorController {

    @Autowired
    private JobOperator jobOperator;

    @Autowired
    private Job jobLauncherDemoJob;
    @GetMapping("/{msg}")
    public String jobRun1(@PathVariable String msg) throws JobParametersInvalidException, JobInstanceAlreadyExistsException, NoSuchJobException {
        //启动任务，同时传参数
        jobOperator.start("jobOperatorDemoJob","msg="+msg);
        return "job success";
    }
}
