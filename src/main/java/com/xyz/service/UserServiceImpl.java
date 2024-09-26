package com.xyz.service;

import java.lang.StackWalker.Option;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyz.Config.JwtProvider;
import com.xyz.exception.UserException;
import com.xyz.models.User;
import com.xyz.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public User findUserById(Long userId) throws UserException {
		// TODO Auto-generated method stub
		Optional<User> opt = userRepository.findById(userId);
		if (opt.isPresent()) {
			return opt.get();
		}
		throw new UserException("User Not Found This id :"+userId);
	}

	@Override
	public User findUserByJwt(String jwt) throws UserException {
		// TODO Auto-generated method stub
		String email = jwtProvider.getEmailFromToken(jwt);
		
		User user = userRepository.findByEmail(email);
		if (user==null) {
			throw new UserException("User Not Found This email :"+email);
		}
		return user;
	}

}
