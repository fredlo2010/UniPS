package com.unips.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.unips.constants.BusinessConstants.UserRoles;
import com.unips.constants.BusinessConstants.UserStatus;
import com.unips.dao.UserDao;
import com.unips.entity.User;

@Repository("user.mysql")
public class UserDaoMysql <T> implements UserDao<T> {
	
	private static class UserResultSetExtractor implements ResultSetExtractor<List<User>> {

		@Override
		public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
			
			Map<Long, User> userMap = new HashMap<>();
			
			while(rs.next()) {
				
				Long id = rs.getLong("id");
				
				// If its not in the map create it
				if (!userMap.containsKey(id)) {
					User user = new User();
					user.setId(rs.getInt("id"));
					user.setCreatedDate(rs.getDate("createdDate"));
					user.setUsername(rs.getString("username"));
					user.setPassword(rs.getString("password"));
					user.setEmail(rs.getString("email"));
					user.setQuestion1(rs.getString("question1"));
					user.setQuestion2(rs.getString("question2"));
					user.setPictureFeatured(rs.getString("pictureFeatured"));
					user.setDescription(rs.getString("description"));
					user.setStatus(UserStatus.values() [rs.getInt("status_id")]);
					user.setRole(UserRoles.values() [rs.getInt("authority_id")]);
					
					List<String> pictures = new LinkedList<>();
					pictures.add(rs.getString("picture"));
					user.setPictures(pictures);
					
					userMap.put(id, user);
					
				} else {
					userMap.get(id).getPictures().add(rs.getString("picture"));
				}
			}
			return new ArrayList<>(userMap.values());
		}
	}

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<User> getAllUsers() {
		
		String sql = "(SELECT * FROM `unipsdb`.`users` AS u " +
					 "LEFT JOIN `unipsdb`.`user_pictures` AS p " +
					 "ON u.id = p.user_id) " +
					 "UNION " +
					 "(SELECT * FROM `unipsdb`.`users` AS u " +
					 "RIGHT JOIN `unipsdb`.`user_pictures` AS p " +
					 "ON u.id = p.user_id);";
		
		List<User> users = jdbcTemplate.query(sql, new UserResultSetExtractor());
		return users;
	}

	@Override
	public T getUserByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T addUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T editUserByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T deleteUserByusername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
