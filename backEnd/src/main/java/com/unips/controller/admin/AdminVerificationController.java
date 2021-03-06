package com.unips.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unips.entity.User;
import com.unips.response.Response;
import com.unips.service.admin.AdminService;

import io.swagger.annotations.Api;

@Controller
@Api(tags="Registration and Login")
public class AdminVerificationController {
	
	@Autowired
	AdminService<User> service;
	
	@RequestMapping(value="api/adminVerification", method=RequestMethod.GET)
	@ResponseBody
	public Response<Boolean> verifyEmail(@RequestParam("token") String candidateToken) {
		boolean status = service.verifyEmail(candidateToken);
		return Response.success(status);
	}
	
	
	@RequestMapping(value="api/adminApproval", method=RequestMethod.GET)
	@ResponseBody
	public Response<Boolean> adminApproval(@RequestParam("token") String candidateToken) {
		boolean status = service.approveAdmin(candidateToken);
		return Response.success(status);
	}
	
	
	@RequestMapping(value="api/businessApproval", method=RequestMethod.GET)
	@ResponseBody
	public Response<Boolean> businessApproval(@RequestParam("token") String candidateToken) {
		boolean status = service.approveBusiness(candidateToken);
		return Response.success(status);
	}
	
}
