import arcpy
import os

arcpy.FeaturesToJSON_conversion("Areas.lyr","AreasFormated.json","FORMATTED")
