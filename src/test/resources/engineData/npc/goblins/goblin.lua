
function _init()
  inherit('npc.monster')
  print("hello from goblin!")
  self:setProperty("test", 1);
  self:setProperty("hp", 10)
end

function foo(n)
  return n
end
