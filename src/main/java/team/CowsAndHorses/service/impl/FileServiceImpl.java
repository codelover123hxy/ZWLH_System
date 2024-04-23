package team.CowsAndHorses.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import team.CowsAndHorses.entity.FileEntity;
import team.CowsAndHorses.mapper.FileMapper;
import team.CowsAndHorses.service.FileService;

@Service
public class FileServiceImpl extends ServiceImpl<FileMapper,FileEntity> implements FileService {

}
