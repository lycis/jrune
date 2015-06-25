-- TODO inherit from npc.monster
-- inherit('npc.monster')
_bp_init = function()
  print("blueprint " .. self:toString() .. " loaded")
end

function _init()
  print("hello from goblin!")
  self:setProperty("test", 1);
end

function foo(n)
  return n
end
