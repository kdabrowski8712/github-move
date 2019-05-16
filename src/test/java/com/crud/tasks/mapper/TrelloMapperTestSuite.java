package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.hibernate.validator.constraints.br.TituloEleitoral;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TrelloMapperTestSuite {

    @Test
    public void testMapToBoards() {

        //Given

       TrelloListDto list1 = new TrelloListDto("1", "testList1",false);
       TrelloListDto list2 = new TrelloListDto("2", "testList2",true);

       List<TrelloListDto> testTrelloList = new ArrayList<>();
       testTrelloList.add(list1);
       testTrelloList.add(list2);

       TrelloBoardDto boardDto = new TrelloBoardDto("1","myboard1",testTrelloList);
       List<TrelloBoardDto> boardDtoList = new ArrayList<>();
       boardDtoList.add(boardDto);

       TrelloMapper trelloMapper = new TrelloMapper();

        //When

        List<TrelloBoard> result = trelloMapper.mapToBoards(boardDtoList);

        //Then

        Assert.assertEquals("1",result.get(0).getId());
        Assert.assertEquals("myboard1",result.get(0).getName());
        Assert.assertEquals("testList1",result.get(0).getLists().get(0).getName());
        Assert.assertEquals(false,result.get(0).getLists().get(0).isClosed());


    }

    @Test
    public void testMapToBoardsDto() {

        //Given

        List<TrelloList> testTrelloList = new ArrayList<>();
        testTrelloList.add(new TrelloList("1", "testList1",false));
        testTrelloList.add(new TrelloList("2", "testList2",true));

        TrelloBoard board = new TrelloBoard("1","myboard1",testTrelloList);
        List<TrelloBoard> boardList = new ArrayList<>();
        boardList.add(board);

        TrelloMapper trelloMapper = new TrelloMapper();

        //When

        List<TrelloBoardDto> trelloBoardDtoList = trelloMapper.mapToBoardsDto(boardList);

        // Then

        Assert.assertEquals("1",trelloBoardDtoList.get(0).getId());
        Assert.assertEquals("myboard1",trelloBoardDtoList.get(0).getName());
        Assert.assertEquals("testList1",trelloBoardDtoList.get(0).getLists().get(0).getName());
        Assert.assertEquals(false,trelloBoardDtoList.get(0).getLists().get(0).isClosed());

    }

    @Test
    public void testMapToList() {

        //given

        List<TrelloListDto> trelloListDtos = new ArrayList<>();
        trelloListDtos.add(new TrelloListDto("1", "testList1",false));
        trelloListDtos.add(new TrelloListDto("2", "testList2",true));

        TrelloMapper trelloMapper = new TrelloMapper();

        //when
        List<TrelloList> trelloList = trelloMapper.mapToList(trelloListDtos);

        //then

        Assert.assertEquals("2",trelloList.get(1).getId());
        Assert.assertEquals("testList2",trelloList.get(1).getName());
        Assert.assertEquals(true, trelloList.get(1).isClosed());


    }

    @Test
    public void testMapToListDto() {

        //given

        List<TrelloList> trelloList = new ArrayList<>();
        trelloList.add(new TrelloList("1", "testList1",false));
        trelloList.add(new TrelloList("2", "testList2",true));

        TrelloMapper trelloMapper = new TrelloMapper();

        //when
        List<TrelloListDto> trelloListDtos = trelloMapper.mapToListDto(trelloList);

        //then

        Assert.assertEquals("2",trelloListDtos.get(1).getId());
        Assert.assertEquals("testList2",trelloListDtos.get(1).getName());
        Assert.assertEquals(true, trelloListDtos.get(1).isClosed());
    }

    @Test
    public void testMapToCardDto() {
        //given

        TrelloCard trelloCard = new TrelloCard("testCard","testDesc","top","1");

        TrelloMapper trelloMapper = new TrelloMapper();

        //when

        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);

        //then

        Assert.assertEquals("1",trelloCardDto.getListId());
        Assert.assertEquals("testCard",trelloCardDto.getName());
        Assert.assertEquals("top",trelloCardDto.getPos());
        Assert.assertEquals("testDesc",trelloCardDto.getDescription());

    }

    @Test
    public void testMapToCard() {
        //given

        TrelloCardDto trelloCardDto = new TrelloCardDto("testCard","testDesc","top","1");

        TrelloMapper trelloMapper = new TrelloMapper();

        //when

        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);

        //then

        Assert.assertEquals("1",trelloCard.getListId());
        Assert.assertEquals("testCard",trelloCard.getName());
        Assert.assertEquals("top",trelloCard.getPos());
        Assert.assertEquals("testDesc",trelloCard.getDescription());

    }
}
