function _bp_init() 
  print(self:getProperty("$entity") .. " blueprint loaded")
end

function _init()
  print(self:getProperty("$entity") .. " initialisation")
  
  self:setProperty("name", "Some monster")
  self:setProperty("hp", 100)
  self:setProperty("$passable", false)
  self:setProperty("description", "some scary monster")
  self:setProperty("inheritedValue", "parent")  
end
