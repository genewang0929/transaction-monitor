//package com.chunhanwang.service;
//
//import com.chunhanwang.entity.*;
//import com.chunhanwang.exception.*;
//import com.chunhanwang.service.*;
//import org.springframework.beans.factory.annotation.*;
//import org.springframework.security.core.userdetails.*;
//import org.springframework.stereotype.*;
//
//import java.util.*;
//
//@Service
//// 因為 SecurityConfig @Autowired UserDetailsService，Spring 會自動找到有實作 UserDetailsService 的類別
//public class SpringUserService implements UserDetailsService {
//    @Autowired
//    private AppUserService appUserService;
//
//    @Override
//    public UserDetails loadUserByUsername(String iban) throws UsernameNotFoundException {
//        try{
//            AppUser user =  appUserService.getUserByIban(iban);
//
//            //該 User 類別是由 org.springframework.security.core.userdetails 的套件提供，本身已經實作 UserDetails，回傳該物件是較簡易的做法。至於建構子的第三個參數是 authorities，是用來定義使用者擁有的權限。
//            return new User(user.getIban(),user.getPassword(), Collections.emptyList());
//        }catch (NotFoundException e){
//            throw new UsernameNotFoundException("This is is wrong.");
//        }
//    }
//}
