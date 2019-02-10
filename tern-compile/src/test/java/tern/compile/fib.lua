--
-- Created by IntelliJ IDEA.
-- User: Niall
-- Date: 03/11/2018
-- Time: 13:17
-- To change this template use File | Settings | File Templates.
--

function fib(m)
    if m < 2 then
        return m
    end
    return fib(m-1) + fib(m-2)
end

local x = os.clock()
local result = fib(30)
local f = os.clock()
print(result)
print(f-x)
