package com.ruoyi.biz.api.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.biz.api.util.FileUtil;
import com.ruoyi.biz.api.util.SecurityCheckUtil;
import com.ruoyi.biz.dto.DictDataDTO;
import com.ruoyi.biz.dto.UploadDTO;
import com.ruoyi.biz.utils.FaceFusionUtils;
import com.ruoyi.biz.utils.UploadTenUtils;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.domain.ApiResult;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.framework.config.ServerConfig;
import com.ruoyi.system.service.ISysDictTypeService;
import com.tencentcloudapi.ft.v20200304.models.FaceRect;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 通用请求处理
 *
 * @author ruoyi
 */
@Api(value = "通用",tags = {"通用接口"})
@RestController
@RequestMapping("/api")
public class CommonApiController
{
    private static final Logger log = LoggerFactory.getLogger(CommonApiController.class);

    @Autowired
    private ServerConfig serverConfig;
    @Autowired
    private ISysDictTypeService dictTypeService;

    /**
     * 通用上传请求
     */
    @ApiOperation(value = "用户上传")
    @PostMapping("/user/upload")
    public ApiResult<UploadDTO> userUploadFile(@RequestPart MultipartFile file) {
        try {
            String filePath = RuoYiConfig.getUploadPath(); // 上传文件路径
            String fileName = FileUploadUtils.upload(filePath, file);  // 上传并返回新文件名称

            String url = serverConfig.getUrl() + fileName;
            String inputPath = filePath + fileName.substring(15); // 原图位置
            String urlNew = UploadTenUtils.uploadVideo(inputPath, "uesrMg", fileName.substring(15));
            FileUtils.deleteFile(inputPath); // 删除原图
            log.info("图片地址:{}",urlNew);
            List<FaceRect> faceRectList = FaceFusionUtils.selectFace(urlNew);

            if (ObjectUtil.isEmpty(faceRectList)) {
                return ApiResult.error("图片中未检测到人脸");
            }


            UploadDTO data = new UploadDTO();
            boolean checkResult = SecurityCheckUtil.imageCheck(urlNew);
            ApiResult ajax;
            if (checkResult){
                data.setFileName(fileName);
                data.setUrl(urlNew);
                ajax = ApiResult.success(data);
            }else{
                ajax = ApiResult.error("图片存在违法违规信息");
            }
            FileUtils.deleteFile(inputPath); // 删除原图
            return ajax;

        }
        catch (Exception e)
        {
            return ApiResult.error(e.getMessage());
        }
    }

    /**
     * 通用上传请求
     */
    @ApiOperation(value = "通用上传")
    @PostMapping("/common/upload")
    public ApiResult<UploadDTO> uploadFile(@RequestPart MultipartFile file) {
        try {
            String filePath = RuoYiConfig.getUploadPath(); // 上传文件路径
            String fileName = FileUploadUtils.upload(filePath, file);  // 上传并返回新文件名称

            String url = serverConfig.getUrl() + fileName;

            String inputPath = filePath + fileName.substring(15); // 原图位置
            String urlNew = UploadTenUtils.uploadVideo(inputPath, "uesr", fileName.substring(15));
            FileUtils.deleteFile(inputPath); // 删除原图
            UploadDTO data = new UploadDTO();
            boolean checkResult = SecurityCheckUtil.imageCheck(url);
            ApiResult ajax;
            if (checkResult){
                data.setFileName(fileName);
                data.setUrl(urlNew);
                ajax = ApiResult.success(data);
            }else{
                ajax = ApiResult.error("图片存在违法违规信息");
            }
            FileUtils.deleteFile(inputPath); // 删除原图
            return ajax;

        }
        catch (Exception e)
        {
            return ApiResult.error(e.getMessage());
        }
    }


    /**
     * 根据字典类型查询字典数据信息
     */
    @ApiOperation("根据字典类型查询字典数据信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dictType",value = "字典类型",dataType = "string",example = "video_template_classify",required = true),
    })
    @GetMapping(value = "/common/dict/{dictType}")
    public ApiResult<List<DictDataDTO>> dictType(@PathVariable String dictType)
    {
        List<SysDictData> data = dictTypeService.selectDictDataByType(dictType);
        List<DictDataDTO> newData = data.stream().map((temp)-> BeanUtil.copyProperties(temp,DictDataDTO.class)).collect(Collectors.toList());
        return ApiResult.success(newData);
    }


    @ApiOperation("通用下载")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileUrl",value = "url",dataType = "string",example = "https://yoface.blueasiainfo.com/profile/upload/2022/01/12/755a0acdfa7947db943b51282be05aa3.mp4",required = true),
    })
    @GetMapping("/common/download")
    public void fileDownload(String fileUrl, HttpServletResponse response) throws IOException {
        InputStream inputStream = null;
        ServletOutputStream outputStream = null;
        try {
            String filePath = RuoYiConfig.getUploadPath() +"/"+ FileUtil.interceptUrl(fileUrl);
            response.setHeader("Content-Disposition", "attachment;filename=" +fileUrl.substring(fileUrl.lastIndexOf("/")+1));
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            inputStream = new FileInputStream(filePath);
            outputStream = response.getOutputStream();
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        }catch (Exception e) {
            log.error("下载文件失败:{}", e.getMessage());
        }finally {
            assert inputStream != null;
            assert outputStream != null;
            inputStream.close();
            outputStream.close();
        }
    }


}
