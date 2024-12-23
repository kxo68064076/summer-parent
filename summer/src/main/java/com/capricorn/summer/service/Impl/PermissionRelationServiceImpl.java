package com.capricorn.summer.service.Impl;

import com.capricorn.summer.mapper.PermissionRelationMapper;
import com.capricorn.summer.service.IPermissionRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PermissionRelationServiceImpl implements IPermissionRelationService {

    @Autowired
    PermissionRelationMapper relationMapper;

    @Override
    public List<Map<String, String>> getAllRoutes() {
        return relationMapper.getAllRoutes();
    }
}
