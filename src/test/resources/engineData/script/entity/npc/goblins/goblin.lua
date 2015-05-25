_bp_init = function()
  print("blueprint " .. self:toString() .. " loaded")
end

function _init()
  print("hello from goblin!")
end

function foo(n)
  return n
end
