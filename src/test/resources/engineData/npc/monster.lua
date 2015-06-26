function _init() 
  print(self:getProperty("$entity") .. " blueprint loaded")
  self:setProperty("name", "Some monster")
  self:setProperty("hp", 100)
  self:setProperty("$passable", false)
  self:setProperty("description", "some scary monster")
  self:setProperty("inheritedValue", "parent")  
end

function _clone()
  print(self:getProperty("$entity") .. " cloned")
end
