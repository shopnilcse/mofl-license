//package com.synesis.mofl.lnm.service;
//
//import static org.junit.jupiter.api.Assertions.assertSame;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.ArrayList;
//import java.util.Optional;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import com.synesis.mofl.lnm.helper.TestMockData;
//import com.synesis.mofl.lnm.model.Department;
//import com.synesis.mofl.lnm.model.Notice;
//import com.synesis.mofl.lnm.payload.NoticePayload;
//import com.synesis.mofl.lnm.repository.DepartmentRepository;
//import com.synesis.mofl.lnm.repository.NoticeRepository;
///**
//*
//* @author Md. Emran Hossain
//* @since 24 Jan, 2022
//*/
//@ContextConfiguration(classes = {NoticeService.class, String.class})
//@ExtendWith(SpringExtension.class)
//class NoticeServiceTest {
//
//    @MockBean
//    private DepartmentRepository departmentRepository;
//
//    @MockBean
//    private NoticeRepository noticeRepository;
//
//    @Autowired
//    private NoticeService noticeService;
//
//    /**
//     * This test for get all Notices
//     *
//     * @author Md. Emran Hossain
//     * @since 24 Jan, 2022
//     */
//    @Test
//    void testGetAllNotice() throws Exception {
//        PageImpl<Notice> pageImpl = new PageImpl<>(new ArrayList<>());
//        when(this.noticeRepository.findAll((Pageable) any())).thenReturn(pageImpl);
//        Page<Notice> actualAllNotice = this.noticeService.getAllNotice(null);
//
//        assertSame(pageImpl, actualAllNotice);
//        assertTrue(actualAllNotice.toList().isEmpty());
//        verify(this.noticeRepository).findAll((Pageable) any());
//    }
//
//    /**
//     * This test for get all Active Notices
//     *
//     * @author Md. Emran Hossain
//     * @since 24 Jan, 2022
//     */
//    @Test
//    void testGetAllActiveNotice() throws Exception {
//        PageImpl<Notice> pageImpl = new PageImpl<>(new ArrayList<>());
//        when(this.noticeRepository.findAllActiveDepartmentNotice((Long) any(), (Pageable) any())).thenReturn(pageImpl);
//        Page<Notice> actualAllActiveNotice = this.noticeService.getAllActiveNotice(123L, null);
//
//        assertSame(pageImpl, actualAllActiveNotice);
//        assertTrue(actualAllActiveNotice.toList().isEmpty());
//        verify(this.noticeRepository).findAllActiveDepartmentNotice((Long) any(), (Pageable) any());
//    }
//
//    /**
//     * This test for get Notice By Id
//     *
//     * @author Md. Emran Hossain
//     * @since 24 Jan, 2022
//     */
//    @Test
//    void testGetNoticeById() throws Exception {
//        Notice notice = TestMockData.getNotice();
//        Optional<Notice> ofResult = Optional.of(notice);
//        when(this.noticeRepository.findById((Long) any())).thenReturn(ofResult);
//
//        assertSame(notice, this.noticeService.getNoticeById(123L));
//        verify(this.noticeRepository).findById((Long) any());
//    }
//
//    /**
//     * This test for save Notice
//     *
//     * @author Md. Emran Hossain
//     * @since 24 Jan, 2022
//     */
//    @Test
//    void testSaveNotice() throws Exception {
//        Notice notice = TestMockData.getNotice();
//        Department department = TestMockData.getDepartment();
//        NoticePayload noticePayload = TestMockData.getNoticePayload();
//
//        when(this.noticeRepository.save((Notice) any())).thenReturn(notice);
//        Optional<Department> ofResult = Optional.of(department);
//        when(this.departmentRepository.findById((Long) any())).thenReturn(ofResult);
//
//        assertSame(notice, this.noticeService.saveNotice(noticePayload));
//        verify(this.noticeRepository).save((Notice) any());
//        verify(this.departmentRepository).findById((Long) any());
//    }
//
//    /**
//     * This test for update Notice
//     *
//     * @author Md. Emran Hossain
//     * @since 24 Jan, 2022
//     */
//    @Test
//    void testUpdateNotice() throws Exception {
//        Notice notice = TestMockData.getNotice();
//        Notice notice1 = TestMockData.getNotice();
//        Optional<Notice> ofResult = Optional.of(notice);
//        Department department = TestMockData.getDepartment();
//        NoticePayload noticePayload = TestMockData.getNoticePayload();
//
//        when(this.noticeRepository.save((Notice) any())).thenReturn(notice1);
//        when(this.noticeRepository.findById((Long) any())).thenReturn(ofResult);
//
//        Optional<Department> ofResult1 = Optional.of(department);
//        when(this.departmentRepository.findById((Long) any())).thenReturn(ofResult1);
//
//        assertSame(notice1, this.noticeService.updateNotice(noticePayload));
//        verify(this.noticeRepository).findById((Long) any());
//        verify(this.noticeRepository).save((Notice) any());
//        verify(this.departmentRepository).findById((Long) any());
//    }
//}
