import React, {useState} from 'react';
import {createSearchParams, useNavigate, useSearchParams} from "react-router-dom";

const getNum = (param, defaultValue) => {

    if(!param){
        return defaultValue
    }
    return parseInt(param)
}


function UseCustomMove(props) {

    const navigate = useNavigate()
    const [refresh, setRefresh] = useState(false)
    const [queryParams] = useSearchParams()

    const page = getNum(queryParams.get('page'), 1)
    const size = getNum(queryParams.get('size'), 10)

    const queryDefault = createSearchParams({page, size}).toString()

    const moveToList = (pageParams) => {

        let queryStr =""

        if(pageParams){
            const pageNum = getNum(pageParams.page, 1)
            const sizeNum = getNum(pageParams.size, 10)

            queryStr = createSearchParams({page:pageNum, size: sizeNum}).toString()
        }else{
            queryStr = queryDefault
        }
        setRefresh(!refresh)
        navigate({pathname:`../list`, search:queryStr})

    }
    const moveToRead = (num) => {
        navigate({
            pathname: `../read/${num}`,
            search:queryDefault
        })
    }

    const moveToModify = (num) =>{
        navigate({
            pathname: `../modify/${num}`,
            search:queryDefault
        })
    }

    return {moveToList, moveToModify,moveToRead, page, size,refresh}
}

export default UseCustomMove;