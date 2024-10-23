import React from 'react'

const LoginButton = (props) => {
  return (
    <button className='bg-indigo-600 text-white font-bold
    font-[Poppins] py-2 px-6 rounded md:ml-8 hover:bg-indigo-400 duration-500'>
        {props.children}
    </button>
  )
}

export default LoginButton