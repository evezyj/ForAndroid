package springbatch.JobLauncherDemo;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class JobLauncherDemo implements StepExecutionListener {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    private Map<String,JobParameter> parameter;

    @Bean
    public Job jobLauncherDemoJob(){
        return jobBuilderFactory.get("jobLauncherDemoJob").start(jobLauncherDemoStep()).build();
    }

    @Bean
    public Step jobLauncherDemoStep() {
        return stepBuilderFactory.get("jobLauncherDemoStep")
                .listener(this)
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println(parameter.get("msg").getValue());
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    //在执行step时获得参数
    @Override
    public void beforeStep(StepExecution stepExecution) {
       parameter =  stepExecution.getJobParameters().getParameters();
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}
