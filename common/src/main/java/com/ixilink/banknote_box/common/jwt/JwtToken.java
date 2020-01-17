package com.ixilink.banknote_box.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ixilink.banknote_box.common.exception.BusinessException;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.pojo.User;
import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtToken {
	
	private static String SECRET="com.ixilink.backBox";

	//创建token
	public static <T extends Object> String creatToken(Map<String, T> claim) throws Exception{
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.HOUR, 10);
		Date expiresdate=calendar.getTime();
		
		Date issuredate=new Date();
		
		Algorithm algorithmHS = Algorithm.HMAC256(SECRET);
		
		Map<String, Object> header = new HashMap<>();
		header.put("alg", "HS256");
		header.put("typ", "JWT");

		return JWT.create()
				.withHeader(header)
		        .withClaim("empNum", (String)claim.get("empNum"))
		        .withExpiresAt(expiresdate)
		        .withIssuedAt(issuredate)
		        .sign(algorithmHS);
	}

	//创建token
	public static String creatToken(User user) throws Exception{
		return JWT.create().withAudience(user.getId().toString())
				.withIssuedAt(new Date())
				.sign(Algorithm.HMAC256(SECRET));
	}

	//验证token
	public static Map<String, Claim> verifyToken(String token) {
		
		DecodedJWT jwt=null;
		Algorithm algorithm=null;
		try {
		    algorithm = Algorithm.HMAC256(SECRET);
		    JWTVerifier verifier = JWT.require(algorithm)
		        .build(); //Reusable verifier instance
		    jwt = verifier.verify(token);
		} catch (JWTVerificationException e){
			if(e instanceof AlgorithmMismatchException) {
				log.error("发生AlgorithmMismatchException 异常：签证不匹配异常");
				throw new BusinessException(Code.TOKEN_FIND_ERROR.getCode(),"非法token认证");
			}
			if(e instanceof InvalidClaimException) {
				log.error("发生InvalidClaimException 异常：无效的claim异常");
				throw new BusinessException(Code.TOKEN_FIND_ERROR.getCode(),"非法token认证");
			}
			if(e instanceof JWTDecodeException) {
				log.error("发生JWTDecodeException 异常：JWT解码异常");
				throw new BusinessException(Code.TOKEN_FIND_ERROR.getCode(),"非法token认证");
			}
			if(e instanceof SignatureVerificationException) {
				log.error("发生SignatureVerificationException 异常：签名验证异常");
				throw new BusinessException(Code.TOKEN_FIND_ERROR.getCode(),"非法token认证");
			}
			if(e instanceof TokenExpiredException) {
				log.error("发生TokenExpiredException 异常：token 过期");
				throw new BusinessException(Code.TOKEN_FIND_ERROR.getCode(),"token 过期");
			}
		}
		
		return jwt.getClaims();
		
	}
}
