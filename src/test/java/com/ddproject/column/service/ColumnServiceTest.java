package com.ddproject.column.service;

import com.ddproject.board.entity.Board;
import com.ddproject.board.repository.BoardRepository;
import com.ddproject.column.entity.Column;
import com.ddproject.column.repository.ColumnRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Log4j2
public class ColumnServiceTest {

    @MockBean
    private BoardRepository boardRepository;
    @MockBean
    private ColumnRepository columnRepository;
    @Autowired
    private ColumnService columnService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("새로운 컬럼 생성 테스트")
    public void createColumnTest() {
        // Given
        Long boardId = 1L; // boardKey 타입을 Long으로 변경
        Board mockBoard = mock(Board.class); // Board 모의 객체 생성

//        when(boardRepository.findByIdAndIsDeletedFalse(boardId)).thenReturn(Optional.of(mock(Board.class))); // findBoardByboardKey 모의 처리

        ColumnDto columnDto = new ColumnDto(null, "Test Column", "Description", 1, boardId);
        Column mockColumn = new Column(); // 새로운 Column 객체 생성
        mockColumn.setName(columnDto.getName());
        mockColumn.setDescription(columnDto.getDescription());
        mockColumn.setSequence(columnDto.getSequence());
        mockColumn.setBoard(mockBoard); // Board 설정

        when(boardRepository.findByIdAndIsDeletedFalse(boardId)).thenReturn(Optional.of(mock(Board.class))); // findBoardByboardKey 모의 처리
        when(columnRepository.save(any())).thenReturn(mockColumn); // save 메소드에 대한 모의 반환값 설정

        // When
        ColumnDto result = columnService.createColumn(columnDto);
        log.info(result);

        // Then
        assertNotNull(result);
        assertEquals(columnDto.getName(), result.getName());
        assertEquals(columnDto.getDescription(), result.getDescription());
        assertEquals(columnDto.getSequence(), result.getSequence());
    }
//    @Test
//    @DisplayName("컬럼 이름 업데이트 테스트")
//    public void updateColumnNameTest() {
//        // Given
//        Long columnId = 1L;
//        String newName = "Updated Name";
//        Column column = new Column();
//        column.setId(columnId);
//        column.setName("Original Name");
//        when(columnRepository.findById(columnId)).thenReturn(Optional.of(column));
//        when(columnRepository.save(any(Column.class))).thenReturn(column);
//
//        // When
//        ColumnDto result = columnService.updateColumnName(columnId, newName);
//
//        // Then
//        assertNotNull(result);
//        assertEquals(newName, result.getName());
//    }
//    @Test
//    @DisplayName("컬럼 시퀀스 업데이트 테스트")
//    public void updateColumnSequenceTest() {
//        // Given
//        Long columnId = 1L;
//        Integer newSequence = 2;
//        Column column = new Column();
//        column.setId(columnId);
//        column.setSequence(1);
//        when(columnRepository.findById(columnId)).thenReturn(Optional.of(column));
//        when(columnRepository.save(any(Column.class))).thenReturn(column);
//        when(customColumnRepository.findColumnsWithSequenceGreaterThanOrEqual(anyLong(), anyLong(), anyInt())).thenReturn(new ArrayList<>());
//
//        // When
//        ColumnDto result = columnService.updateColumnSequence(columnId, newSequence);
//
//        // Then
//        assertNotNull(result);
//        assertEquals(newSequence, result.getSequence());
//    }
//    @Test
//    @DisplayName("컬럼 삭제 테스트")
//    public void deleteColumnTest() {
//
//        // Given
//        Long columnId = 1L;
//
//        // When
//        columnService.deleteColumn(columnId);
//
//        // Then
//        verify(columnRepository, times(1)).deleteById(columnId);
//    }
//    @Test
//    @DisplayName("보드의 모든 컬럼 가져오기 테스트")
//    public void getAllColumnsTest() {
//        // Given
//        Long boardId = 1L;
//        List<Column> columns = Arrays.asList(new Column(), new Column());
//        when(customColumnRepository.findAllColumnsByBoardIdOrderedBySequence(boardId)).thenReturn(columns);
//
//        // When
//        List<ColumnDto> result = columnService.getAllColumns(boardId);
//
//        // Then
//        assertNotNull(result);
//        assertEquals(2, result.size());
//    }
}