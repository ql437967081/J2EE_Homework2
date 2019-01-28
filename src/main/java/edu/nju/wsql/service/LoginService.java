package edu.nju.wsql.service;

public interface LoginService {
    boolean checkIdAndPassword(int id, String password);
}
