package com.duowan.generator.controller;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import com.duowan.generator.common.util.CmdExecutor;
import com.duowan.generator.common.util.TaskExecResult;
import com.duowan.generator.common.util.ZipHelper;
import com.duowan.generator.controller.TableGeneratorController.GenCmdExecutor;

/**
 * 基于项目的生成器
 * 
 * @author badqiu
 *
 */
@Controller
@RequestMapping("/projectgenerator")
public class ProjectGeneratorController {

	@RequestMapping
	public void gen(String archetypeArtifactId,GenCmdExecutor cmd,HttpServletRequest request,HttpServletResponse response) throws Exception {
//		archetypeArtifactId = StringUtils.defaultIfEmpty(archetypeArtifactId,"maven-archetype-quickstart");
		Assert.hasText(archetypeArtifactId,"archetypeArtifactId must be not blank");
		Assert.hasText(cmd.projectId,"projectId must be not blank");
		Assert.hasText(cmd.basepackage,"basepackage must be not blank");
		
		String outputDirectory = getOutputDir();
		String execCmd = " mvn archetype:generate -DgroupId="+cmd.basepackage+" -DartifactId="+cmd.projectId+" -DarchetypeArtifactId="+archetypeArtifactId+" -DinteractiveMode=false -DoutputDirectory="+outputDirectory;
		TaskExecResult result = execCmd(execCmd);
		if(result.getExitValue() == 0) {
			response.setHeader("Content-Disposition", "attachment; filename=\"" + cmd.projectId + "_project_output.zip" + "\"");
			ZipHelper.zip(outputDirectory,response.getOutputStream());
			FileUtils.deleteDirectory(new File(outputDirectory));
			FileUtils.deleteQuietly(new File(outputDirectory));
			new File(outputDirectory).delete();
		}else {
			FileUtils.deleteDirectory(new File(outputDirectory));
			FileUtils.deleteQuietly(new File(outputDirectory));
			new File(outputDirectory).delete();
			throw new RuntimeException("exec cmd error:"+execCmd+" exitValue"+result.getExitValue()+" log:"+ToStringBuilder.reflectionToString(result));
		}
	}

	private String getOutputDir() {
		String outputDirectory = System.getProperty("java.io.tmpdir")+"project_generator_output/"+System.currentTimeMillis();
		if(SystemUtils.IS_OS_WINDOWS) {
			outputDirectory = outputDirectory.replace('/','\\');
		}
		new File(outputDirectory).mkdirs();
		return outputDirectory;
	}

	private TaskExecResult execCmd(String execCmd) throws IOException,InterruptedException {
		System.out.println("projectGen,cmd:"+execCmd+" ");
		if(SystemUtils.IS_OS_WINDOWS) {
//			cd = "cmd /c \""+cd+"\""; 
			execCmd = "cmd /c \"" + execCmd.trim()+"\""; 
		}
		
		TaskExecResult result = CmdExecutor.execCmdForTaskExecResult(execCmd);
		return result;
	}
	
}
