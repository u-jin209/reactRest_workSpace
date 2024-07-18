import { Link } from "react-router-dom";

const BasicMenu = () => {
  return (  
  <nav id='navbar' className=" flex  border-2">

    <div className="w-4/5 " >
      <ul className="flex p-4 text-black font-bold">
        <li className="pr-6 text-2xl">
          <Link to={'/'}>Main</Link>
        </li>
        <li className="pr-6 text-2xl">
          <Link to={'/about'}>About</Link>
        </li>
        <li className="pr-6 text-2xl">
          <Link to={'/todo/'}>Todo</Link>
        </li>
      </ul>
    </div>

    <div className="w-1/5 flex justify-end  p-4 font-medium">
        <div className="text-black text-sm m-1 rounded" >
          Login
        </div>
    </div>
  </nav>
  );
}
 
export default BasicMenu;
