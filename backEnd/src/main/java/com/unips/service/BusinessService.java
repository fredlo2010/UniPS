package com.unips.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;

import com.unips.constants.BusinessConstants.Roles;
import com.unips.constants.BusinessConstants.Status;
import com.unips.dao.BusinessDao;
import com.unips.dao.BusinessReviewDao;
import com.unips.dao.UserInfoDao;
import com.unips.entity.Business;
import com.unips.entity.UserInfo;
import com.unips.mail.SmptMailSender;
import com.unips.response.Response;

@Service
public class BusinessService<T>{

	private static final int VALID_MAX_COUNT_ONE = 1;

	@Autowired
	@Qualifier("business.mysql")
	BusinessDao businessDao;

	@Autowired
	@Qualifier("businessReview.mysql")
	BusinessReviewDao businessReviewDao;
	
	@Autowired
	@Qualifier("userInfo.mysql")
	UserInfoDao userInfoDao;
	
	@Autowired
	SmptMailSender mailSender;

	
	public Response<List<Business>> getAllBusiness() {
		try {
			List<Business> business = businessDao.getAllBusiness();
			return  Response.success(business);
			
		} catch (Exception e) {
			return Response.failure(e.getMessage());
		}	
	}

	
	public Response<Business> getBusiness(String username) {
		
		try {
			return  Response.success(businessDao.getBusiness(username));
		} catch (Exception e) {
			return Response.failure(e.getMessage());
		}
	}

	
	public Response<Business> addBusiness(Business business) {
		
		// Make sure the user does not exits
		UserInfo userInfo = userInfoDao.getUserInfo(business.getUsername());

		if (userInfo != null)
			return Response.failure("User already exists");
		
		try {
			// Add created fields
			int updated_records = 0;
			
			ShaPasswordEncoder encode = new ShaPasswordEncoder();
			business.setPassword(encode.encodePassword(business.getPassword(), null));
			business.setToken(UUID.randomUUID().toString());
			business.setStatus(Status.DISABLED);
			business.setRole(Roles.ROLE_BUSINESS);
			
			updated_records = businessDao.addBusiness(business);
	
			// Check updated records and send email
			if (updated_records != VALID_MAX_COUNT_ONE)
				return Response.failure("More than one record updated in the database");
			
			// Send Email
			try {
				String url = "http://localhost:8080/api/userVerification?token=" + business.getToken();
				mailSender.sendUserVerificationEmail(business.getEmail(), url);
			} catch (Exception e) {
				// Let it go....
			}
			
			return Response.success(business);
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.failure(e.getMessage());
		}
	}
	

	@PreAuthorize("hasAnyRole('ADMIN') or #username == authentication.getName()")
	public Response<Business> updateBusiness(Business business) {
		
		try {
		
			// Encode the password
			ShaPasswordEncoder encode = new ShaPasswordEncoder();
			business.setPassword(encode.encodePassword(business.getPassword(), null));
			
			return Response.success(businessDao.updateBusiness(business));
			
		} catch (Exception e) {
			return Response.failure(e.getMessage());
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN') or #username == authentication.getName()")
	public Response<Integer> deleteBusiness(String username) {
		try {
			return Response.success(businessDao.deleteBusiness(username));
		} catch (Exception e) {
			return Response.failure(e.getMessage());
		}
	}
	

}