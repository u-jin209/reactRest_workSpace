import React, {useRef, useState} from 'react';
import {postAdd} from "../../api/productsApi";
import FetchingModal from "../common/FetchingModal";
import ResultModal from "../common/ResultModal";
import UseCustomMove from "../../hooks/useCustomMove";

const initState = {
    pname:"",
    pdesc:'',
    price:0,
    files:[]
}



function AddComponent(props) {
    const [product, setProduct] = useState(initState)
    const [fetching, setFetching] = useState(false)
    const [result, setResult] = useState(false)
    const uploadRef = useRef()
    const {moveToList}  = UseCustomMove();

    const handleChangeProduct = (e) => {
        product[e.target.name] = e.target.value
        setProduct({...product})
    }
    const handleClickAdd = (e) => {
        console.log(product)

        const formData = new FormData()
        const files = uploadRef.current.files

        console.log("files")
        console.log(files)

        for(let i =0; i< files.length; i++){
            console.log("sssss")
            formData.append("files",files[i] )
        }

        formData.append("pname",product.pname)
        formData.append("price",product.price)
        formData.append("pdesc",product.pdesc)
        console.log(formData.get("files"))


        postAdd(formData).then(data => {
            setFetching(false)
            setResult(data.RESULT)
        })
    }

    const closeModal = () => {
        setResult(null)
        moveToList()
    }
    return (
        <div className="border-2 border-sky-200 nt-10 m-2 p-4">
            <div className="flex justify-center">
                <div className="relative mb-4 flex w-full flex-wrap items-stretch">
                    <div className="w-1/5 p-6 text-right font-bold">Product Name</div>
                    <input className="w-4/5 p-6 rounded-r border border-solid border-neutral-500 shadow-md"
                           name="pname"
                           type={'text'}
                           value={product.pname}
                           onChange={handleChangeProduct}
                    />
                </div>
            </div>
            <div className="flex justify-center">
                <div className="relative mb-4 flex w-full flex-wrap items-stretch">
                    <div className="w-1/5 p-6 text-right font-bold">Description</div>

                    <textarea
                        className="w-4/5 p-6 rounded-r border border-solid border-neutral-300 shadow-md resize-y"
                        name="pdesc"
                        rows="4"
                        onChange={handleChangeProduct}
                        value={product.pdesc}>
                      {product.pdesc}
                    </textarea>
                </div>
            </div>
            <div className="flex justify-center">
                <div className="relative mb-4 flex w-full flex-wrap items-stretch">
                    <div className="w-1/5 p-6 text-right font-bold">Price</div>
                    <input className="w-4/5 p-6 rounded-r border border-solid border-neutral-500 shadow-md"
                           name="price"
                           type={'number'}
                           value={product.price}
                           onChange={handleChangeProduct}
                    />
                </div>
            </div>
            <div className="flex justify-center">
                <div className="relative mb-4 flex w-full flex-wrap items-stretch">
                    <div className="w-1/5 p-6 text-right font-bold">FILES</div>
                    <input className="w-4/5 p-6 rounded-r border border-solid border-neutral-500 shadow-md"
                           name="files"
                           multiple={true}
                           type={'file'}
                           ref={uploadRef}
                    />
                </div>
            </div>
            <div className="flex justify-end">
                <div className="relative mb-4 flex p-4 flex-wrap items-stretch">
                    <button type="button"
                            className="rounded p-4 w-36 bg-blue-500 text-xl  text-white "
                            onClick={handleClickAdd}
                    >
                        ADD
                    </button>

                </div>
            </div>
            {fetching ? <FetchingModal/> : <></>}

            {result ? <ResultModal callbackFn={closeModal} title={"Product Add Result"} content={`${result}번 상품 등록`}/> : <></>}
        </div>
    );
}

export default AddComponent;