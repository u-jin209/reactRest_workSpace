import React, {useEffect, useState} from 'react';
import {deleteOne, getOne, postAdd, putOne} from "../../api/todoApi";
import ResultModal from "../common/ResultModal";
import UseCustomMove from "../../hooks/useCustomMove";
import useCustomMove from "../../hooks/useCustomMove";
import PageComponent from "../common/PageComponent";

const initState = {
    tno : 0,
    title : '',
    writer : '',
    content : '',
    dueDate : '',
    complete : false
}

function ModifyComponent({tno}) {
    const [todo, setTodo] = useState(initState)
    const [result, setResult] = new useState(null)
    const { moveToRead, moveToList } = useCustomMove()

    const handleChangeTodo = (e) => {
        console.log(e.target.name, e.target.value)
        todo[e.target.name] = e.target.value
        setTodo({...todo})
    }
    const handleClickModify = () => {
        putOne(todo).then( result =>
        {
            console.log(result)
            setResult("Modify")

        })
    }
    const handelClickDelete = () =>{
        deleteOne(todo.tno).then(result => {
            console.log("delete - " + result)
            setResult("Delete")
        })
    }

    const  closeModal = () => {
        if(result === 'Delete'){
            moveToList()
        }else{
            moveToRead(tno)
        }
    }
    useEffect(() => {
        getOne(tno).then(data => {
            console.log(data)
            setTodo(data)
        })
    }, [tno]);

    const handleChangeTodoComplete = (e) =>{
        const value = e.target.value
        todo.complete = (value === 'Y')
        setTodo({...todo})
    }
    return (
        <div className="border-2 border-sky-200 mt-10 m-2 p-4">
            <div className="flex justify-center">
                <div className="relative mb-4 flex w-full flex-wrap items-stretch">
                    <div className="w-1/5 p-6 text-right font-bold">TITLE</div>
                    <input className="w-4/5 p-6 rounded-r border border-solid border-neutral-500 shadow-md"
                           name="title"
                           type={'text'}
                           value={todo.title}
                           onChange={handleChangeTodo}
                    />
                </div>
            </div>
            <div className="flex justify-center">
                <div className="relative mb-4 flex w-full flex-wrap items-stretch">
                    <div className="w-1/5 p-6 text-right font-bold">WRITER</div>
                    <input className="w-4/5 p-6 rounded-r border border-solid border-neutral-500 shadow-md"
                           name="writer"
                           type={'text'}
                           value={todo.writer}
                           onChange={handleChangeTodo}
                    />
                </div>
            </div>
            <div className="flex justify-center">
                <div className="relative mb-4 flex w-full flex-wrap items-stretch">
                    <div className="w-1/5 p-6 text-right font-bold">CONTENT</div>
                    <input className="w-4/5 p-6 rounded-r border border-solid border-neutral-500 shadow-md"
                           name="content"
                           type={'text'}
                           value={todo.content}
                           onChange={handleChangeTodo}
                    />
                </div>
            </div>
            <div className="flex justify-center">
                <div className="relative mb-4 flex w-full flex-wrap items-stretch">
                    <div className="w-1/5 p-6 text-right font-bold">DUE DATE</div>
                    <input className="w-4/5 p-6 rounded-r border border-solid border-neutral-500 shadow-md"
                           name="dueDate"
                           type={'date'}
                           value={todo.dueDate}
                           onChange={handleChangeTodo}
                    />
                </div>
            </div>
            <div className="flex justify-center">
                <div className='relative mb-4 flex w-full flex-wrap items-stretch'>
                    <div className="w-1/5 p-6 text-right font-bold">COMPLETE</div>
                    <select
                        name='status'
                        className='border-solid border-2 rounded m-1 p-2'
                        onChange={handleChangeTodoComplete}
                        value={todo.complete ? 'Y' : 'N'}>
                        <option value='Y'>Completed</option>
                        <option value='N'>Not Yet</option>
                    </select>
                </div>
            </div>
            <div className="flex justify-end p-4">
                <button type='button'
                        className='inline-block rounded p-4 m-2 text-xl w-32 text-white bg-red-500'
                        onClick={() => handelClickDelete()}>Delete
                </button>
                <button type='button'
                        className='inline-block rounded p-4 m-2 text-xl w-32 text-white bg-blue-500'
                        onClick={() => handleClickModify()}>Modify
                </button>
            </div>
            {result ? <ResultModal title={'처리 결과'} content={result} callbackFn={closeModal}/> :<></> }
        </div>
    );
}

export default ModifyComponent;