package com.example.damo_practica2_v3;

import java.util.ArrayList;
import java.util.List;

public class GameData
{
    public String m_title;
    public String m_description;
    public String m_year;
    public String m_company;

    public static final List<GameData> m_gameDataList = new ArrayList<GameData>();

    public GameData (String title, String description, String year, String company) {
        m_title = title;
        m_description = description;
        m_year = year;
        m_company = company;
    }
}
