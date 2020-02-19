package com.daihao.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private static final int SALT_SIZE = 7;
    private static final String TYPE_ENCRY = "MD5";
    private static final int LOOP_TIME = 5;

    public String createEncryptedPassword(String password)
    {
        String key = null;
        String salt = null;
        try
        {
            salt = getRandomSalt(SALT_SIZE);
            String encryStr = getEncryptedStr(password, salt, TYPE_ENCRY, LOOP_TIME);

            //Key trả về sẽ có dạng $<loại mã hóa>$<muối>$<số lần mã hóa>$<chuỗi đã mã hóa>
            key = "$" + TYPE_ENCRY + "$" + salt + "$" + LOOP_TIME + "$" + encryStr;
            return key;
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return  null;
    }

    //lấy ra chuỗi mã hóa dựa của chuỗi truyền vào kèm theo salt và thời gian lặp
    private String getEncryptedStr(String str, String salt, String typeEncry, int timeLoop) throws NoSuchAlgorithmException {
        String encryptedStr = str;
        MessageDigest mdSHA = MessageDigest.getInstance(TYPE_ENCRY);
        for (int i = 0; i < LOOP_TIME; i++)
        {
            encryptedStr = salt + encryptedStr;
            encryptedStr = DatatypeConverter.printHexBinary(mdSHA.digest(encryptedStr.getBytes()));
        }
        return encryptedStr;
    }

    private String getRandomSalt(int size)
    {
        String str = "ABCDEFGHIJKLMNOPQRSTVWXYZabcdefghijklmnopqrstvwxyz1234567890";
        String salt = "";
        for (int i = 0; i < size; i++)
        {
            int randomIndex = new Random().nextInt(str.length());
            salt += str.substring(randomIndex, randomIndex + 1);
        }
        return salt;
    }

    //Trả về true nếu mật khẩu truyền vào trùng với mật khẩu đã mã hóa
    public boolean checkPassword(String password, String hashPassword) throws NoSuchAlgorithmException {

        //phân tích hashPassword lấy ra kiểu mã hóa, salt, số lần lặp, chuỗi mã hóa
        String[] dataHash = hashPassword.split("\\$", 0);

        String type = dataHash[1];
        String salt = dataHash[2];
        int loopTime = Integer.parseInt(dataHash[3]);
        String hash = dataHash[4];

        String hash_temp = getEncryptedStr(password, salt, type, loopTime);

        return hash_temp.equals(hash);
    }

    public static void main(String[] args) throws InterruptedException, NoSuchAlgorithmException {
        AuthenticationService service = new AuthenticationService();

//        while ( true )
//        {
//            System.out.println(new AuthenticationService().createEncryptedPassword("daihao"));
//            Thread.sleep(100);
//        }

//        System.out.println(service.getEncryptedStr("daihao", "53XRdMF", "MD5", 5));
        boolean yes = service.checkPassword("daihao", "$MD5$MaaRKLM$5$6F9F6B8E46D9E804F3B122F9C6199226");
        String testPass = service.createEncryptedPassword("123");
        System.out.println(yes);
        System.out.println(testPass);
    }
}
